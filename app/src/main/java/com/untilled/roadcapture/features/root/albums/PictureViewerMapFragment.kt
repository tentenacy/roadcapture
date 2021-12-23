package com.untilled.roadcapture.features.root.albums

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.UiSettings
import com.untilled.roadcapture.R
import com.untilled.roadcapture.databinding.FragmentPictureViewerMapBinding
import com.untilled.roadcapture.utils.extension.getPxFromDp
import com.untilled.roadcapture.utils.extension.navigationHeight
import com.untilled.roadcapture.utils.extension.statusBarHeight

class PictureViewerMapFragment : Fragment(), OnMapReadyCallback {
    private var _binding : FragmentPictureViewerMapBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PictureViewerContainerViewModel by viewModels({requireParentFragment()})

    private var naverMap: NaverMap? = null
    private var uiSettings: UiSettings? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPictureViewerMapBinding.inflate(inflater, container, false)

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
    }
}