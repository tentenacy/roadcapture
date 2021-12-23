package com.untilled.roadcapture.features.root.capture

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import android.view.Gravity.END
import android.view.Gravity.TOP
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.overlay.Marker
import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.entity.Picture
import com.untilled.roadcapture.databinding.FragmentCaptureBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.net.toUri
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.CompositePermissionListener
import com.karumi.dexter.listener.single.PermissionListener
import com.karumi.dexter.listener.single.SnackbarOnDeniedPermissionListener
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import com.untilled.roadcapture.utils.extension.*
import com.untilled.roadcapture.utils.getCircularBitmap


@AndroidEntryPoint
class CaptureFragment : Fragment(), OnMapReadyCallback {
    private var _binding: FragmentCaptureBinding? = null
    private val binding get() = _binding!!

    private var imageUri: Uri? = null

    private var picture: Picture? = null

    private var naverMap: NaverMap? = null
    private var uiSettings: UiSettings? = null
    private lateinit var locationSource: FusedLocationSource
    private var markerList: MutableList<Marker> = mutableListOf()

    // 갤러리 사진 가져오는 intent 콜백 등록
    private val getContent = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            imageUri = it.data?.data
            Navigation.findNavController(binding.root)
                .navigate(CaptureFragmentDirections.actionCaptureFragmentToCropFragment(imageUri.toString()))
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

        initNaverMap()
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().setStatusBarTransparent()

        binding.coordinatorCapture.setPadding(
            0,
            requireContext().statusBarHeight(),
            0,
            requireContext().navigationHeight()
        )

        setOnClickListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        requireActivity().setStatusBarOrigin()

        _binding = null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setOnClickListeners() {
        binding.imageviewCaptureBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.imageviewCaptureCamera.setOnClickListener {
            requestSinglePermission(
                Manifest.permission.CAMERA,
                "사진을 찍기위해서는 카메라 권한이 필요합니다. 설정으로 이동합니다.",
            ) {
                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_captureFragment_to_cameraFragment)
            }
        }
        binding.imageviewCaptureGallery.setOnClickListener {
            pickFromGallery()
        }
        binding.imageviewCapturePlay.setOnClickListener {
            // Todo 앨범을 등록하시겠습니까 bottomsheet 띄우기, 썸네일 여부 체크하여 알려주기
            if(markerList.isEmpty()) {
                showThumbnailSettingDialog()
            } else {
                Navigation.findNavController(binding.root)
                    .navigate(
                        CaptureFragmentDirections.actionCaptureFragmentToAlbumRegestrationFragment(
                            picture = picture
                        )
                    )
            }
        }
        binding.imageviewCaptureStop.setOnClickListener {
            if(markerList.isNotEmpty()) {
                showCancelAlbumCreationAskingDialog {
                    for (i in markerList) {
                        i.map = null    // 지도에서 마커 제거
                    }
                    markerList.clear()  // 마커 리스트 클리어
                    deleteCache(requireContext())   // 캐시 디렉토리에 있는 사진들 제거
                    picture = null
                    requireArguments().clear() // navArgs 도 함께 초기화해야 함 (안그러면 마커 그릴때 에러)
                }
            }
        }
    }

    private fun getNavArgs() {
        val args: CaptureFragmentArgs by navArgs()
        if (args.picture != null) {
            picture = args.picture

            drawMarker(picture!!)
        }
    }

    private fun initNaverMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.fragment_map) as? MapFragment
            ?: MapFragment.newInstance().also {
                childFragmentManager.beginTransaction().add(R.id.fragment_map, it).commit()
            }
        mapFragment.getMapAsync(this)
    }

    private fun initNaverMapUiSetting() {
        uiSettings = naverMap!!.uiSettings
        uiSettings?.isCompassEnabled = false // 나침반 비활성화
        uiSettings?.isZoomControlEnabled = false // 확대 축소 버튼 비활성화
        uiSettings?.isScaleBarEnabled = false // 스케일 바 비활성화
        uiSettings?.isLocationButtonEnabled = false // 기본 내 위치 버튼 비활성화
        binding.location.map = naverMap // 내 위치 버튼 설정

        uiSettings?.logoGravity = TOP
        uiSettings?.logoGravity = END
        uiSettings?.setLogoMargin(
            0,
            requireContext().getPxFromDp(16f) + requireContext().statusBarHeight(),
            requireContext().getPxFromDp(16f),
            0
        )
    }

    private fun drawMarker(picture: Picture) {
        val marker = Marker()

        marker.apply {
            icon = OverlayImage.fromBitmap(
                picture.imageUri!!.toUri().getCircularBitmap(
                    requireContext(),
                    72f,
                    72f
                )!!
            )
            position = LatLng(
                picture?.searchResult?.locationLatLng?.latitude?.toDouble()
                    ?: 37.5670135,
                picture?.searchResult?.locationLatLng?.longitude?.toDouble()
                    ?: 126.9783740
            )
            onClickListener = Overlay.OnClickListener {     // 마커 클릭 이벤트
                Navigation.findNavController(binding.root)
                    .navigate(
                        CaptureFragmentDirections.actionCaptureFragmentToPictureEditorFragment(
                            picture = picture
                        )
                    )
                return@OnClickListener true
            }

        }.map = naverMap

        markerList.add(marker)

        naverMap?.moveCamera(CameraUpdate.scrollTo(marker.position))
    }


    override fun onMapReady(_naverMap: NaverMap) {
        _naverMap.isLiteModeEnabled = true
        naverMap = _naverMap
        naverMap!!.locationSource = locationSource

        initNaverMapUiSetting()

        requestSinglePermission(
            Manifest.permission.ACCESS_FINE_LOCATION,
            "지도에 현재 위치를 표시하기 위해서는 위치권한이 필요합니다. 설정으로 이동합니다."
        ) {
            //Todo 현재 위치 지도에 표시
            //naverMap?.locationTrackingMode = LocationTrackingMode.Follow
        }
        getNavArgs()
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

    private fun requestLocationPermission(startRecord: () -> Unit) {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            startRecord()
        } else {
            requestSinglePermission(
                Manifest.permission.ACCESS_FINE_LOCATION,
                "여행을 기록하기 위해서는 위치권한 허용이 필요합니다. 설정으로 이동합니다."
            ) {
                startRecord()
            }
        }
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
        val layoutInflater = LayoutInflater.from(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.alert_dialog_cancel_album_creation_asking, null)

        val dialog = AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog)
            .setView(dialogView)
            .create()

        val textViewConfirm = dialogView.findViewById<TextView>(R.id.textview_cancel_album_creation_asking_confirm)
        val textViewCancel = dialogView.findViewById<TextView>(R.id.textview_cancel_album_creation_asking_cancel)

        textViewConfirm?.setOnClickListener {
            logic()
            dialog.dismiss()
        }
        textViewCancel?.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showThumbnailSettingDialog() {
        val layoutInflater = LayoutInflater.from(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.alert_dialog_thumbnail_setting, null)

        val dialog = AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog)
            .setView(dialogView)
            .create()

        val textViewConfirm = dialogView.findViewById<TextView>(R.id.textview_thumbnail_setting_confirm)

        textViewConfirm?.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
}