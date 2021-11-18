package com.untilled.roadcapture.features.root.capture

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.entity.Picture
import com.untilled.roadcapture.databinding.FragmentCaptureBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.net.toUri
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.CompositeMultiplePermissionsListener
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.multi.SnackbarOnAnyDeniedMultiplePermissionsListener
import com.karumi.dexter.listener.single.CompositePermissionListener
import com.karumi.dexter.listener.single.PermissionListener
import com.karumi.dexter.listener.single.SnackbarOnDeniedPermissionListener
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.overlay.OverlayImage
import com.untilled.roadcapture.utils.extension.navigationHeight
import com.untilled.roadcapture.utils.extension.setStatusBarOrigin
import com.untilled.roadcapture.utils.extension.setStatusBarTransparent
import com.untilled.roadcapture.utils.extension.statusBarHeight
import com.untilled.roadcapture.utils.getCircularBitmap


@AndroidEntryPoint
class CaptureFragment : Fragment(), OnMapReadyCallback {
    private var _binding: FragmentCaptureBinding? = null
    private val binding get() = _binding!!

    private var imageUri: Uri? = null

    private var naverMap: NaverMap? = null

    private var picture: Picture? = null

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

        initNaverMap()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().apply {
            setStatusBarTransparent()
            binding.coordinatorCapture.setPadding(0, requireContext().statusBarHeight(), 0, requireContext().navigationHeight() )
        }

        setOnClickListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        requireActivity().apply {
            setStatusBarOrigin()
            binding.coordinatorCapture.setPadding(0, 0, 0, 0)
        }

        _binding = null
    }

    private fun setOnClickListeners() {
        binding.fabCaptureCapture.setOnClickListener {
            //requestCameraPermission()
            requestSinglePermission(
                Manifest.permission.CAMERA,
                "사진을 찍기위해서는 카메라 권한이 필요합니다. 설정으로 이동합니다.",
            ) { Navigation.findNavController(binding.root)
                    .navigate(R.id.action_captureFragment_to_cameraFragment)
            }
        }
        binding.imageviewCaptureBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.fabCapturePickerGallery.setOnClickListener {
            pickFromGallery()
        }
        binding.fabCaptureRecord.setOnClickListener {
            requestLocationPermission {
                // Todo 기록시작
                Toast.makeText(requireContext(), "기록 시작", Toast.LENGTH_SHORT).show()
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
        }.map = naverMap

        naverMap?.moveCamera(CameraUpdate.scrollTo(marker.position))
    }

    override fun onMapReady(_naverMap: NaverMap) {
        _naverMap.isLiteModeEnabled = true
        naverMap = _naverMap

        requestSinglePermission(
            Manifest.permission.ACCESS_FINE_LOCATION,
            "지도에 현재 위치를 표시하기 위해서는 위치권한이 필요합니다. 설정으로 이동합니다."
        ){
            //Todo 현재 위치 지도에 표시
        }
        getNavArgs()
    }

    private fun requestLocationPermission(startRecord: () -> Unit) {
        when(Build.VERSION.SDK_INT) {
            in 23..28 -> {  // android 9.0 (api 28 이하)
                requestSinglePermission(Manifest.permission.ACCESS_FINE_LOCATION,
                    "여행 기록을 위해서는 위치권한 항상 허용이 필요합니다. 설정으로 이동합니다."
                ) {
                    startRecord()
                }
            }
            29 -> { // android 10.0 (api 29)
                if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    requestSinglePermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                        "여행을 기록하기 위해서는 위치권한 항상 허용이 필요합니다. 설정으로 이동합니다."
                    ) {
                        startRecord()
                    }
                } else {
                    requestMultiplePermission(arrayListOf(
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION),
                        "여행 기록을 위해서는 위치권한 항상 허용이 필요합니다. 설정으로 이동합니다."
                    ) {
                        startRecord()
                    }
                }
            }
            30 -> { // android 11.0 (api 30)
                when (PackageManager.PERMISSION_GRANTED) {
                    ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_BACKGROUND_LOCATION) -> {
                        startRecord()
                    }
                    ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) -> {
                        showBackgroundPermissionDialog {
                            requestSinglePermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                                "여행을 기록하기 위해서는 위치권한 항상 허용이 필요합니다. 설정으로 이동합니다."
                            ) {
                                startRecord()
                            }
                        }
                    }
                    else -> {
                        requestSinglePermission(Manifest.permission.ACCESS_FINE_LOCATION,
                            "여행을 기록하기 위해서는 위치권한 항상 허용이 필요합니다. 설정으로 이동합니다."
                        ) {
                            showBackgroundPermissionDialog {
                                requestSinglePermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                                    "여행을 기록하기 위해서는 위치권한 항상 허용이 필요합니다. 설정으로 이동합니다."
                                ) {
                                    startRecord()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun requestSinglePermission(permission: String, deniedMessage: String, logic: () -> Unit) {
        val basicPermissionListener : PermissionListener = object : PermissionListener {
            override fun onPermissionGranted(p0: PermissionGrantedResponse?) { logic() } // 권한 허용 됬을때
            override fun onPermissionDenied(p0: PermissionDeniedResponse?) { }    // 권한 거부 됬을대
            override fun onPermissionRationaleShouldBeShown(p0: PermissionRequest?, p1: PermissionToken?) { }
        }
        val snackbarPermissionListener: PermissionListener =
            SnackbarOnDeniedPermissionListener.Builder
                .with(view, deniedMessage)
                .withOpenSettingsButton("설정")
                .withCallback(object : Snackbar.Callback() {
                    override fun onShown(snackbar: Snackbar) { }
                    override fun onDismissed(snackbar: Snackbar, event: Int) { }
                }).build()

        Dexter.withContext(requireContext())
            .withPermission(permission)
            .withListener(CompositePermissionListener(basicPermissionListener, snackbarPermissionListener)).check()
    }

    private fun requestMultiplePermission(permissions: ArrayList<String>, deniedMessage: String, logic: () -> Unit ) {
        val basicMultiplePermissionListener : MultiplePermissionsListener = object :
            MultiplePermissionsListener {
            override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                logic()
            }
            override fun onPermissionRationaleShouldBeShown(
                p0: MutableList<PermissionRequest>?,
                p1: PermissionToken?
            ) {
                Toast.makeText(requireContext(), "거절됨", Toast.LENGTH_SHORT).show()
            }
        }

        val snackbarMultiplePermissionsListener: MultiplePermissionsListener =
            SnackbarOnAnyDeniedMultiplePermissionsListener.Builder
                .with(view, deniedMessage)
                .withOpenSettingsButton("설정")
                .withCallback(object : Snackbar.Callback() {
                    override fun onShown(snackbar: Snackbar) { }
                    override fun onDismissed(snackbar: Snackbar, event: Int) {  }
                })
                .build()

        Dexter.withContext(requireContext())
            .withPermissions(permissions)
            .withListener(CompositeMultiplePermissionsListener(basicMultiplePermissionListener, snackbarMultiplePermissionsListener)).check()
    }

    private fun showBackgroundPermissionDialog(requestPermission: () -> Unit) {
        val builder = AlertDialog.Builder(requireContext(), R.style.DialogTheme)
        builder.setTitle("위치 권한 항상 허용")
            .setMessage("여행을 기록하기 위해서는 위치권한 항상 허용이 필요합니다. 설정으로 이동합니다.")
            .setPositiveButton("확인") { _, _ ->
                requestPermission()
            }
            .setNegativeButton("취소") {_,_ -> }
            .create()
            .show()
    }
}