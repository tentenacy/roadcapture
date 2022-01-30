package com.untilled.roadcapture.features.picture

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.overlay.PathOverlay
import com.untilled.roadcapture.R
import com.untilled.roadcapture.databinding.FragmentPictureMapBinding
import com.untilled.roadcapture.utils.getPxFromDp
import com.untilled.roadcapture.utils.navigationHeight

class PictureMapFragment : Fragment(), OnMapReadyCallback {
    private var _binding: FragmentPictureMapBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PictureViewerViewModel by viewModels({ requireParentFragment() })

    private var naverMap: NaverMap? = null
    private var markerList: MutableList<Marker> = mutableListOf()
    private var path = PathOverlay()

    private val markerOnClickListener = Overlay.OnClickListener {
        //Todo: 마커 클릭 시 해당 순서 slide 로 이동
        true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPictureMapBinding.inflate(inflater, container, false)

        initNaverMap()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(_naverMap: NaverMap) {
        naverMap = _naverMap
        initNaverMapUiSetting()
        drawMarker()
        drawPolyline()
        moveCamera()
    }

    private fun initNaverMap() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.fragmentcontainer_picture_map) as? MapFragment
                ?: MapFragment.newInstance().also {
                    childFragmentManager.beginTransaction()
                        .add(R.id.fragmentcontainer_picture_map, it).commit()
                }
        mapFragment.getMapAsync(this)
    }

    private fun initNaverMapUiSetting() {
        naverMap?.isLiteModeEnabled = true
        naverMap?.uiSettings?.run {
            isCompassEnabled = false // 나침반 비활성화
            isZoomControlEnabled = false // 확대 축소 버튼 비활성화
            isScaleBarEnabled = false // 스케일 바 비활성화
            isLocationButtonEnabled = false // 기본 내 위치 버튼 비활성화
            setLogoMargin(
                requireContext().getPxFromDp(16f),
                0,
                0,
                requireContext().navigationHeight() + requireContext().getPxFromDp(16f)
            )
        }
    }

    private fun moveCamera() {
        naverMap?.moveCamera(CameraUpdate.scrollTo(markerList.first().position))
    }

    private fun drawMarker() {
        viewModel.album.value?.pictures?.map { picture ->
            val marker = Marker()
            marker.position = LatLng(
                picture.place.latitude.toDouble(),
                picture.place.longitude.toDouble()
            )
            marker.isHideCollidedMarkers = true
            marker.zIndex = if (picture.thumbnail) 100 else 0
            marker.onClickListener = markerOnClickListener
            addMarkerImage(marker, picture.imageUrl)
            markerList.add(marker)
        }
    }

    private fun drawPolyline() {
        val coords = mutableListOf<LatLng>()
        markerList.forEach { marker ->
            coords.add(marker.position)
        }
        if (coords.size > 1) {
            path.color = Color.parseColor("#3d86c7")
            path.outlineColor = Color.parseColor("#3d86c7")
            path.outlineWidth = requireContext().getPxFromDp(3f)
            path.coords = coords
            path.map = naverMap
        }
    }

    private fun addMarkerImage(marker: Marker, imageUrl: String) {
        Glide.with(requireContext()).asBitmap().load(imageUrl)
            .apply(RequestOptions().centerCrop().circleCrop())
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    marker.icon = OverlayImage.fromBitmap(
                        Bitmap.createScaledBitmap(
                            resource,
                            requireContext().getPxFromDp(64f),
                            requireContext().getPxFromDp(64f),
                            true
                        )
                    )
                    marker.map = naverMap
                }
                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }
}