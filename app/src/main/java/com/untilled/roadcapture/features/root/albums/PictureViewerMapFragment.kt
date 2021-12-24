package com.untilled.roadcapture.features.root.albums

import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.overlay.PathOverlay
import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.entity.Picture
import com.untilled.roadcapture.databinding.FragmentPictureViewerMapBinding
import com.untilled.roadcapture.utils.DummyDataSet
import com.untilled.roadcapture.utils.extension.getPxFromDp
import com.untilled.roadcapture.utils.extension.navigationHeight

class PictureViewerMapFragment : Fragment(), OnMapReadyCallback {
    private var _binding : FragmentPictureViewerMapBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PictureViewerContainerViewModel by viewModels({requireParentFragment()})

    private var naverMap: NaverMap? = null
    private var uiSettings: UiSettings? = null
    private lateinit var path : PathOverlay

    private lateinit var pictureList: List<Picture>
    private var markerList = mutableListOf<Marker>()

    private var latitude: Float = 0f
    private var longitude: Float = 0f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPictureViewerMapBinding.inflate(inflater, container, false)

        pictureList = DummyDataSet.picture
        initNaverMap()

        return binding.root
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

        uiSettings?.setLogoMargin(
            requireContext().getPxFromDp(16f),0,0, requireContext().navigationHeight() + requireContext().getPxFromDp(16f)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(_naverMap: NaverMap) {
        _naverMap.isLiteModeEnabled = true

        naverMap = _naverMap
        initNaverMapUiSetting()

        for(i in pictureList) {
            drawMarker(i)
            latitude += i.searchResult?.locationLatLng!!.latitude
            longitude += i.searchResult?.locationLatLng!!.longitude
        }

        drawPolyline()

        naverMap?.moveCamera(CameraUpdate.scrollTo(LatLng(latitude/pictureList.size.toDouble(), longitude/pictureList.size.toDouble())))
    }

    private fun drawMarker(picture: Picture) {
        val marker = Marker()

        marker.apply {
            position = LatLng(
                picture?.searchResult?.locationLatLng?.latitude?.toDouble()
                    ?: 37.5670135,
                picture?.searchResult?.locationLatLng?.longitude?.toDouble()
                    ?: 126.9783740
            )
            isHideCollidedMarkers = true    // 마커 겹치면 사라지기
            //zIndex = 0    //  zIndex로 마커들 겹쳤을때 우선순위 정할 수 있음 (썸네일로 설정된 사진이 zIndex가장 높게)
        }

        Glide.with(requireContext()).asBitmap().load(picture.imageUri!!.toUri())
            .apply(RequestOptions().centerCrop().circleCrop()).into(object : SimpleTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                val bitmap = Bitmap.createScaledBitmap(resource, requireContext().getPxFromDp(64f), requireContext().getPxFromDp(64f), true)
                marker.apply {
                    icon = OverlayImage.fromBitmap(bitmap)
                }.map = naverMap
            }
        })

        markerList.add(marker)
    }

    private fun drawPolyline() {
        path = PathOverlay()

        val _coords = mutableListOf<LatLng>()
        for(i in markerList) {
            _coords.add(i.position)
        }

        path.apply {
            color = Color.parseColor("#3d86c7")
            outlineColor = Color.parseColor("#3d86c7")
            outlineWidth = requireContext().getPxFromDp(3f)
            coords = _coords
        }.map = naverMap
    }
}