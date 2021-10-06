package com.untilled.roadcapture.features.root.capture

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
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
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.overlay.OverlayImage
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

        setOnClickListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setOnClickListeners() {
        binding.fabCaptureCapture.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_captureFragment_to_cameraFragment)
        }
        binding.imageviewCaptureBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.fabCapturePickerGallery.setOnClickListener {
            pickFromGallery()
        }
        binding.fabCaptureRecord.setOnClickListener {

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
                    250,
                    250
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

        getNavArgs()
    }
}