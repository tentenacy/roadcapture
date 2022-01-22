package com.untilled.roadcapture.features.root.capture

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import android.view.Gravity.END
import android.view.Gravity.TOP
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.naver.maps.map.overlay.Marker
import com.untilled.roadcapture.R
import com.untilled.roadcapture.databinding.FragmentCaptureBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
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


@AndroidEntryPoint
class CaptureFragment : Fragment(), OnMapReadyCallback {
    private var _binding: FragmentCaptureBinding? = null
    val binding get() = _binding!!

    private val viewModel: CaptureViewModel by viewModels()

    private var imageUri: Uri? = null

    private var naverMap: NaverMap? = null
    private var uiSettings: UiSettings? = null
    private lateinit var locationSource: FusedLocationSource
    private var markerList: MutableList<Marker> = mutableListOf()
    private var path = PathOverlay()

    // 갤러리 사진 가져오는 intent 콜백 등록
    private val getContent = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            imageUri = it.data?.data
            navigateToCrop(imageUri.toString())
        }
    }

    private fun pickFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
            .setType(MediaStore.Images.Media.CONTENT_TYPE)
            .setType("image/*")

        getContent.launch(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCaptureBinding.inflate(inflater, container, false)

        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

        viewModel.pictureList.observe(viewLifecycleOwner) { }

        initNaverMap()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.constraintCaptureInnercontainer.setStatusBarTransparent(requireActivity())

        setOnClickListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        requireActivity().setStatusBarOrigin()

        _binding = null
    }

    private fun setOnClickListeners() {
        binding.imageCaptureBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.imageCaptureCamera.setOnClickListener {
            requestSinglePermission(
                Manifest.permission.CAMERA,
                "사진을 찍기위해서는 카메라 권한이 필요합니다. 설정으로 이동합니다.",
            ) {
                navigateToCamera()
            }
        }
        binding.imageCaptureGallery.setOnClickListener {
            pickFromGallery()
        }
        binding.imageCaptureRegistration.setOnClickListener {
            if (!viewModel.pictureList.value.isNullOrEmpty()) {
                var picture : Picture? = null
                for(p in viewModel.pictureList.value!!) {
                    if(p.thumbnail) {
                        picture = p
                    }
                }
                if(picture == null) {
                    showThumbnailSettingDialog()
                } else {
                    navigateToAlbumRegistration(picture)
                }
            }
        }
        binding.imageCaptureCancel.setOnClickListener {
            if (markerList.isNotEmpty()) {
                showCancelAlbumCreationAskingDialog {
                    for (i in markerList) {
                        i.map = null    // 지도에서 마커 제거
                    }
                    markerList.clear()  // 마커 리스트 클리어
                    path.map = null     // 지도에서 경로 제거
                    deleteCache(requireContext())   // 캐시 디렉토리에 있는 사진들 제거
                    viewModel.deleteAll()   // Room에 저장된 picture 모두 제거
                }
            }
        }
    }

    private fun initNaverMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.fragmentcontainer_capture) as? MapFragment
            ?: MapFragment.newInstance().also {
                childFragmentManager.beginTransaction().add(R.id.fragmentcontainer_capture, it).commit()
            }
        mapFragment.getMapAsync(this)
    }

    private fun initNaverMapUiSetting() {
        uiSettings = naverMap!!.uiSettings
        uiSettings?.isCompassEnabled = false // 나침반 비활성화
        uiSettings?.isZoomControlEnabled = false // 확대 축소 버튼 비활성화
        uiSettings?.isScaleBarEnabled = false // 스케일 바 비활성화
        uiSettings?.isLocationButtonEnabled = false // 기본 내 위치 버튼 비활성화
        binding.btnCaptureLocation.map = naverMap // 내 위치 버튼 설정

        uiSettings?.logoGravity = TOP
        uiSettings?.logoGravity = END
        uiSettings?.setLogoMargin(
            0,
            requireContext().getPxFromDp(16f) + requireContext().statusBarHeight(),
            requireContext().getPxFromDp(16f),
            0
        )
    }

    private fun drawPolyline() {
        val _coords = mutableListOf<LatLng>()
        for(i in markerList) {
            _coords.add(i.position)
        }

        if(_coords.size >= 2) {
            path.apply {
                color = Color.parseColor("#3d86c7")
                outlineColor = Color.parseColor("#3d86c7")
                outlineWidth = requireContext().getPxFromDp(3f)
                coords = _coords
            }.map = naverMap
        }
    }

    private fun drawMarker() {
        if (!viewModel.pictureList.value.isNullOrEmpty()) {
            for (picture in viewModel.pictureList.value!!) {
                val marker = Marker()
                marker.apply {
                    position = LatLng(
                        picture.place?.latitude?.toDouble() ?: 37.5670135,
                        picture.place?.longitude?.toDouble() ?: 126.9783740,
                    )
                    isHideCollidedMarkers = true    // 마커 겹치면 사라지기
                    zIndex = if (picture.thumbnail) 100 else 0  // 썸네일 마커가 가장 위에 표시
                    onClickListener = Overlay.OnClickListener {     // 마커
                        navigateToPictureEditor(picture)
                        return@OnClickListener true
                    }
                }
                Glide.with(requireContext()).asBitmap().load(picture.imageUrl!!.toUri())
                    .apply(RequestOptions().centerCrop().circleCrop())
                    .into(object : SimpleTarget<Bitmap>() {
                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                            val bitmap = Bitmap.createScaledBitmap(
                                resource,
                                requireContext().getPxFromDp(64f),
                                requireContext().getPxFromDp(64f),
                                true
                            )
                            marker.apply {
                                icon = OverlayImage.fromBitmap(bitmap)
                            }.map = naverMap
                        }
                    })
                markerList.add(marker)
            }
            naverMap?.moveCamera(CameraUpdate.scrollTo(markerList.last().position))
        }
    }

    override fun onMapReady(_naverMap: NaverMap) {
        _naverMap.isLiteModeEnabled = true
        naverMap = _naverMap
        naverMap!!.locationSource = locationSource

        initNaverMapUiSetting()

        requestSinglePermission(
            Manifest.permission.ACCESS_FINE_LOCATION,
            "지도에 현재 위치를 표시하기 위해서는 위치권한이 필요합니다. 설정으로 이동합니다."
        ) { }

        drawMarker()
        drawPolyline()
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

    private fun requestSinglePermission(
        permission: String,
        deniedMessage: String,
        logic: () -> Unit
    ) {
        val basicPermissionListener: PermissionListener = object : PermissionListener {
            override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                logic()
            } // 권한 허용 됬을때

            override fun onPermissionDenied(p0: PermissionDeniedResponse?) {}    // 권한 거부 됬을대
            override fun onPermissionRationaleShouldBeShown(
                p0: PermissionRequest?,
                p1: PermissionToken?
            ) {
            }
        }
        val snackbarPermissionListener: PermissionListener =
            SnackbarOnDeniedPermissionListener.Builder
                .with(view, deniedMessage)
                .withOpenSettingsButton("설정")
                .withCallback(object : Snackbar.Callback() {
                    override fun onShown(snackbar: Snackbar) {}
                    override fun onDismissed(snackbar: Snackbar, event: Int) {}
                }).build()

        Dexter.withContext(requireContext())
            .withPermission(permission)
            .withListener(
                CompositePermissionListener(
                    basicPermissionListener,
                    snackbarPermissionListener
                )
            ).check()
    }

    private fun showCancelAlbumCreationAskingDialog(logic: () -> Unit) {
        val dialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.dlg_albumcreation_cancel, null)

        val dialog = AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog)
            .setView(dialogView)
            .create()

        dialogView.findViewById<TextView>(R.id.text_dlgalbumcreationcancel_confirm)
            ?.setOnClickListener {
                logic()
                dialog.dismiss()
            }
        dialogView.findViewById<TextView>(R.id.text_dlgalbumcreationcancel_cancel)
            ?.setOnClickListener {
                dialog.dismiss()
            }

        dialog.show()
    }

    private fun showThumbnailSettingDialog() {
        val dialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.dlg_thumbnail_setting, null)

        val dialog = AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog)
            .setView(dialogView)
            .create()

        dialogView.findViewById<TextView>(R.id.text_dlgthumbnailsetting_confirm)
            ?.setOnClickListener {
                dialog.dismiss()
            }

        dialog.show()
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
}