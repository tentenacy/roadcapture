package com.untilled.roadcapture.features.root.capture

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import android.view.Gravity.END
import android.view.Gravity.TOP
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.naver.maps.map.overlay.Marker
import com.untilled.roadcapture.R
import com.untilled.roadcapture.databinding.FragmentCaptureBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.CompositePermissionListener
import com.karumi.dexter.listener.single.PermissionListener
import com.karumi.dexter.listener.single.SnackbarOnDeniedPermissionListener
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.overlay.PathOverlay
import com.naver.maps.map.util.FusedLocationSource
import com.untilled.roadcapture.data.entity.Picture
import com.untilled.roadcapture.utils.*
import com.untilled.roadcapture.utils.permission.*


@AndroidEntryPoint
class CaptureFragment : Fragment(), OnMapReadyCallback {
    private var _binding: FragmentCaptureBinding? = null
    val binding get() = _binding!!

    private val viewModel: CaptureViewModel by viewModels()

    private var imageUri: Uri? = null

    private var naverMap: NaverMap? = null
    private var uiSettings: UiSettings? = null
    private var markerList: MutableList<Marker> = mutableListOf()
    private var path = PathOverlay()
    private lateinit var locationSource: FusedLocationSource

    // 갤러리 사진 가져오는 intent 콜백 등록
    private val getImageActivityResultContract =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                imageUri = it.data?.data
                navigateToCrop(imageUri.toString())
            }
        }

    private val onBackPressed: (View?) -> Unit = {
        mainActivity().onBackPressed()
    }

    private val galleryOnClickListener: (View?) -> Unit = {
        pickFromGallery()
    }

    private val cameraOnClickListener: (View?) -> Unit = {
        if (checkPermission(CAMERA)) {
            navigateToCamera()
        } else {
            requestCameraRequest()
        }
    }

    private val registrationOnClickListener: (View?) -> Unit = {
        viewModel.pictureList.value?.let {
            it.forEach { picture ->
                if (picture.thumbnail) {
                    navigateToAlbumRegistration(picture)
                    return@let
                }
            }
            showThumbnailSettingDialog()
        }
    }

    private val cancelOnClickListener: (View?) -> Unit = {
        if (markerList.isNotEmpty()) {
            showCancelAlbumCreationAskingDialog {
                markerList.forEach {   // 지도에서 마커 제거
                    it.map = null
                }
                markerList.clear()  // 마커 리스트 클리어
                path.map = null     // 지도에서 경로 제거
                deleteCache(requireContext())   // 캐시 디렉토리에 있는 사진들 제거
                viewModel.deleteAll()   // Room에 저장된 picture 모두 제거
            }
        }
    }

    private val locationBasicPermissionListener = basicPermissionListener {
        Toast.makeText(requireContext(), "위치 권한을 허용하였습니다.", Toast.LENGTH_SHORT).show()
    }

    private val locationSnackBarPermissionListener by lazy {
        snackBarPermissionListener(view, "지도에 현재 위치를 표시하기 위해서는 위치권한이 필요합니다. 설정으로 이동합니다.")
    }

    private val cameraBasicPermissionListener = basicPermissionListener {
        Toast.makeText(requireContext(), "카메라 권한을 허용하였습니다.", Toast.LENGTH_SHORT).show()
        navigateToCamera()
    }

    private val cameraSnackBarPermissionListener by lazy {
        snackBarPermissionListener(view, "사진을 찍기위해서는 카메라 권한이 필요합니다. 설정으로 이동합니다.")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCaptureBinding.inflate(inflater, container, false)
        mainActivity().viewModel.setBindingRoot(binding.root)

        initLocationSource()
        initLocationSource()
        initNaverMap()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.constraintCaptureInnercontainer.setStatusBarTransparent(mainActivity())
        requestLocationPermission()
        observeData()
        setOnClickListeners()
    }

    private fun observeData() {
        viewModel.pictureList.observe(viewLifecycleOwner) {

        }
    }

    private fun setOnClickListeners() {
        binding.imageCaptureBack.setOnClickListener(onBackPressed)
        binding.imageCaptureGallery.setOnClickListener(galleryOnClickListener)
        binding.imageCaptureCamera.setOnClickListener(cameraOnClickListener)
        binding.imageCaptureRegistration.setOnClickListener(registrationOnClickListener)
        binding.imageCaptureCancel.setOnClickListener(cancelOnClickListener)
    }

    override fun onMapReady(_naverMap: NaverMap) {
        naverMap = _naverMap

        setNaverMapUI()
        drawMarker()
        drawPolyline()
    }

    private fun initLocationSource() {
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
    }

    private fun initNaverMap() {
        val naverMap =
            childFragmentManager.findFragmentById(R.id.fragmentcontainer_capture) as? MapFragment
                ?: MapFragment.newInstance().also {
                    childFragmentManager.beginTransaction()
                        .add(R.id.fragmentcontainer_capture, it)
                        .commit()
                }
        naverMap.getMapAsync(this)
    }

    private fun setNaverMapUI() {
        naverMap?.apply {
            this.locationSource = this@CaptureFragment.locationSource
            isLiteModeEnabled = true

            this@CaptureFragment.uiSettings = this.uiSettings.apply {
                isCompassEnabled = false // 나침반 비활성화
                isZoomControlEnabled = false // 확대 축소 버튼 비활성화
                isScaleBarEnabled = false // 스케일 바 비활성화
                isLocationButtonEnabled = false // 기본 내 위치 버튼 비활성화
                logoGravity = TOP
                logoGravity = END
                binding.btnCaptureLocation.map = naverMap // 내 위치 버튼 설정
                setLogoMargin(
                    0,
                    requireContext().getPxFromDp(16f) + requireContext().statusBarHeight(),
                    requireContext().getPxFromDp(16f),
                    0
                )
            }
        }
    }

    private fun drawPolyline() {
        markerList.map {
            it.position
        }.also {
            if (it.size >= 2) {
                path.apply {
                    color = Color.parseColor("#3d86c7")
                    outlineColor = Color.parseColor("#3d86c7")
                    outlineWidth = requireContext().getPxFromDp(3f)
                    coords = it
                    map = naverMap
                }
            }
        }
    }

    private fun drawMarker() {
        viewModel.pictureList.value?.let {
            it.forEach { picture ->
                markerList.add(
                    Marker().apply {
                        isHideCollidedMarkers = true    // 마커 겹치면 사라지기
                        zIndex = if (picture.thumbnail) 100 else 0  // 썸네일 마커가 가장 위에 표시
                        position = LatLng(
                            picture.place?.latitude ?: 37.5670135,
                            picture.place?.longitude ?: 126.9783740,
                        )
                        onClickListener = Overlay.OnClickListener {     // 마커
                            navigateToPictureEditor(picture)
                            return@OnClickListener true
                        }
                        setCircularImageMarker(this, picture.fileUri!!)
                        map = naverMap
                    }
                )
            }
            if(it.isNotEmpty()) {
                naverMap?.moveCamera(CameraUpdate.scrollTo(markerList.last().position))
            }
        }
    }

    private fun setCircularImageMarker(marker: Marker, imgUri: String) {
        Glide.with(requireContext())
            .asBitmap()
            .load(imgUri.toUri())
            .apply(RequestOptions().centerCrop().circleCrop())
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    marker.apply {
                        icon = OverlayImage.fromBitmap(
                            Bitmap.createScaledBitmap(
                                resource,
                                requireContext().getPxFromDp(64f),
                                requireContext().getPxFromDp(64f),
                                true
                            )
                        )
                    }
                }
                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }

    private fun pickFromGallery() {
        getImageActivityResultContract.launch(
            Intent(Intent.ACTION_PICK).apply {
                type = MediaStore.Images.Media.CONTENT_TYPE
                type = "image/*"
            }
        )
    }

    private fun requestLocationPermission() {
        requestSinglePermission(
            FINE_LOCATION,
            locationBasicPermissionListener,
            locationSnackBarPermissionListener
        )
    }

    private fun requestCameraRequest() {
        requestSinglePermission(
            CAMERA,
            cameraBasicPermissionListener,
            cameraSnackBarPermissionListener
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()

        mainActivity().setStatusBarOrigin()
        _binding = null
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions,
                grantResults
            )
        ) {
            if (!locationSource.isActivated) { // 권한 거부됨
                naverMap?.locationTrackingMode = LocationTrackingMode.None
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
}


