package com.untilled.roadcapture.features.root.capture

import android.Manifest
import android.animation.ObjectAnimator
import android.app.Activity.RESULT_OK
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
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
import androidx.core.view.WindowCompat
import androidx.core.view.isVisible
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
import com.untilled.roadcapture.utils.extension.*
import com.untilled.roadcapture.utils.getCircularBitmap


@AndroidEntryPoint
class CaptureFragment : Fragment(), OnMapReadyCallback {
    companion object {
        private const val RUN: Int = 1
        private const val PAUSE: Int = 2
        private const val STOP: Int = 3
    }

    private var _binding: FragmentCaptureBinding? = null
    private val binding get() = _binding!!

    private var imageUri: Uri? = null

    private var naverMap: NaverMap? = null

    private var picture: Picture? = null

    private var status: Int = STOP

    private var navigationBarColor: Int = 0

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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().run {
            setStatusBarTransparent()
            if(Build.VERSION.SDK_INT >= 30) {
                navigationBarColor = window.navigationBarColor
                window.navigationBarColor = Color.TRANSPARENT
            }
        }

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

        requireActivity().run {
            setStatusBarOrigin()
            if(Build.VERSION.SDK_INT >= 30) {
                window.navigationBarColor = navigationBarColor
            }
        }

        _binding = null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setOnClickListeners() {
        binding.fabCaptureCapture.setOnClickListener {
            //requestCameraPermission()
            requestSinglePermission(
                Manifest.permission.CAMERA,
                "사진을 찍기위해서는 카메라 권한이 필요합니다. 설정으로 이동합니다.",
            ) {
                Navigation.findNavController(binding.root)
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
            when (status) {
                STOP -> requestLocationPermission {     // 초기 상태(정지)
                    // Todo 기록시작
                    status = RUN
                    binding.fabCaptureRecord.setImageResource(R.drawable.ic_pause)
                    binding.fabCaptureStop.show()
                    Toast.makeText(requireContext(), "기록 시작", Toast.LENGTH_SHORT).show()
                }
                RUN -> {    // 기록 중
                    status = PAUSE
                    ObjectAnimator.ofFloat(
                        binding.fabCaptureStop,
                        "translationX",
                        requireContext().getPxFromDp(32f).toFloat()
                    ).apply { start() }
                    ObjectAnimator.ofFloat(
                        binding.fabCaptureRecord,
                        "translationX",
                        -requireContext().getPxFromDp(32f).toFloat()
                    ).apply { start() }
                    binding.fabCaptureRecord.setImageResource(R.drawable.ic_play)
                    Toast.makeText(requireContext(), "일시정지", Toast.LENGTH_SHORT).show()
                }
                PAUSE -> {  // 일시 정지
                    status = RUN
                    ObjectAnimator.ofFloat(binding.fabCaptureStop, "translationX", 0f)
                        .apply { start() }
                    ObjectAnimator.ofFloat(binding.fabCaptureRecord, "translationX", 0f)
                        .apply { start() }
                    binding.fabCaptureRecord.setImageResource(R.drawable.ic_pause)
                    Toast.makeText(requireContext(), "기록 재개", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.fabCaptureStop.setOnClickListener {
            when (status) {
                PAUSE -> {
                    status = STOP
                    val askRegisterAlbumBottomSheetDialog = AlbumRegistrationAskingBottomSheetDialog()
                    askRegisterAlbumBottomSheetDialog.show(
                        childFragmentManager,
                        "askRegisterAlbumBottomSheetDialog"
                    )
                    ObjectAnimator.ofFloat(binding.fabCaptureStop, "translationX", 0f)
                        .apply {
                            start()
                            binding.fabCaptureStop.hide()
                        }
                    ObjectAnimator.ofFloat(binding.fabCaptureRecord, "translationX", 0f).apply {
                        start()
                    }
                    Toast.makeText(requireContext(), "기록 중지", Toast.LENGTH_SHORT).show()
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
        ) {
            //Todo 현재 위치 지도에 표시
        }
        getNavArgs()
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
}