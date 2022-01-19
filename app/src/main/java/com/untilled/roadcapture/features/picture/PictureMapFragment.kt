package com.untilled.roadcapture.features.picture

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.PathOverlay
import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.datasource.api.dto.picture.PictureResponse
import com.untilled.roadcapture.databinding.FragmentPictureMapBinding
import com.untilled.roadcapture.utils.dummy.DummyDataSet
import com.untilled.roadcapture.utils.getPxFromDp
import com.untilled.roadcapture.utils.navigationHeight

class PictureMapFragment : Fragment(), OnMapReadyCallback {
    private var _binding : FragmentPictureMapBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PictureViewerViewModel by viewModels({requireParentFragment()})

    private var naverMap: NaverMap? = null
    private var uiSettings: UiSettings? = null
    private lateinit var path : PathOverlay

    private lateinit var pictureResponseList: List<PictureResponse>
    private var markerList = mutableListOf<Marker>()

    private var latitude: Float = 0f
    private var longitude: Float = 0f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPictureMapBinding.inflate(inflater, container, false)

       // pictureResponseList = DummyDataSet.picture
        initNaverMap()

        return binding.root
    }

    private fun initNaverMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.fragmentcontainer_picture_map) as? MapFragment
            ?: MapFragment.newInstance().also {
                childFragmentManager.beginTransaction().add(R.id.fragmentcontainer_picture_map, it).commit()
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

//        for(i in pictureList) {
//            drawMarker(i)
//            latitude += i.searchResult?.locationLatLng!!.latitude
//            longitude += i.searchResult?.locationLatLng!!.longitude
//        }

        drawPolyline()

        naverMap?.moveCamera(CameraUpdate.scrollTo(LatLng(latitude/pictureResponseList.size.toDouble(), longitude/pictureResponseList.size.toDouble())))
    }

//    private fun drawMarker(picture: Picture) {
//        val marker = Marker()
//
//        marker.apply {
//            position = LatLng(
//                picture?.searchResult?.locationLatLng?.latitude?.toDouble()
//                    ?: 37.5670135,
//                picture?.searchResult?.locationLatLng?.longitude?.toDouble()
//                    ?: 126.9783740
//            )
//            isHideCollidedMarkers = true    // 마커 겹치면 사라지기
//            //zIndex = 0    //  zIndex로 마커들 겹쳤을때 우선순위 정할 수 있음 (썸네일로 설정된 사진이 zIndex가장 높게)
//        }
//
//        Glide.with(requireContext()).asBitmap().load(picture.imageUri!!.toUri())
//            .apply(RequestOptions().centerCrop().circleCrop()).into(object : SimpleTarget<Bitmap>() {
//            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                val bitmap = Bitmap.createScaledBitmap(resource, requireContext().getPxFromDp(64f), requireContext().getPxFromDp(64f), true)
//                marker.apply {
//                    icon = OverlayImage.fromBitmap(bitmap)
//                }.map = naverMap
//            }
//        })
//
//        markerList.add(marker)
//    }

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