package com.untilled.roadcapture.features.root.capture

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.untilled.roadcapture.data.datasource.api.dto.address.Address
import com.untilled.roadcapture.data.datasource.api.dto.place.PlaceRequest
import com.untilled.roadcapture.data.datasource.api.dto.poi.Poi
import com.untilled.roadcapture.data.datasource.api.dto.poi.Pois
import com.untilled.roadcapture.data.entity.Picture
import com.untilled.roadcapture.databinding.FragmentPlaceSearchBinding
import com.untilled.roadcapture.features.common.CustomDivider
import com.untilled.roadcapture.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class PlaceSearchFragment : Fragment() {
    private var _binding: FragmentPlaceSearchBinding? = null
    private val binding get() = _binding!!

    private val searchViewModel: PlaceSearchViewModel by viewModels()

    private lateinit var picture: Picture

    private var placeList: List<PlaceRequest>? = listOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlaceSearchBinding.inflate(inflater, container, false)

        initAdapter()

        searchViewModel.searchPlaceResponse.observe(viewLifecycleOwner) { searchPlaceResponse ->

            binding.progressbarPlaceSearchLoading.isVisible = false // 로딩 애니메이션 off

            if (searchPlaceResponse != null) {
                poisToPlace(searchPlaceResponse.searchPoiInfo.pois)
            } else {
                displayNoResult()
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: PlaceSearchFragmentArgs by navArgs()
        if (args.picture != null) {
            picture = args.picture!!
            picture.place?.name?.let {
                binding.edtPlaceSearchInput.setText(it)

                displayLoadingAnimation()
                searchViewModel.getSearchPlace(it)
            }
        }
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.imagePlaceSearchBack.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(
                    PlaceSearchFragmentDirections.actionSearchPlaceFragmentToPictureEditorFragment(
                        picture = picture
                    )
                )
        }

        binding.edtPlaceSearchInput.setOnEditorActionListener { v, actionId, event ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    (v as EditText).text.toString().run {
                        if (isNullOrBlank()) {
                            Toast.makeText(requireContext(), "검색어를 입력해 주세요.", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            displayLoadingAnimation()
                            searchViewModel.getSearchPlace(this)
                        }
                    }
                    requireActivity().hideKeyboard(binding.edtPlaceSearchInput)
                    return@setOnEditorActionListener true
                }
                else -> return@setOnEditorActionListener false
            }
        }
    }

    private fun initAdapter() {
        val customDivider = CustomDivider(2.5f, 1f, Color.parseColor("#EFEFEF"))

        binding.recyclerPlaceSearch.addItemDecoration(customDivider)
    }
    
    private fun poisToPlace(pois: Pois) {
        placeList = pois.poi.map {
            PlaceRequest(
                // todo 생성날짜 표시
                placeCreatedAt = "",
                placeLastModifiedAt = "",
                latitude = it.noorLat,
                longitude = it.noorLon,
                name = it.name ?: "",
                Address(
                    addressName = makeAddressNumber(it),
                    roadAddressName = makeRoadName(it),
                    region1DepthName = it.upperAddrName ?: "",
                    region2DepthName = it.middleAddrName ?: "",
                    region3DepthName = it.lowerAddrName ?: "",
                    zoneNo = ""
                )
            )
        }
    }

    private fun displayNoResult() {
        binding.textPlaceSearchNoresult.isVisible = true
        placeList = null   // 결과 리스트 초기화
    }

    private fun displayLoadingAnimation() {
        binding.progressbarPlaceSearchLoading.isVisible = true  // 로딩 애니메이션 on
        binding.textPlaceSearchNoresult.isVisible = false
    }

    private fun makeAddressNumber(poi: Poi): String =
        if (poi.secondNo?.trim().isNullOrEmpty()) {
            (poi.upperAddrName?.trim() ?: "") + " " +
                    (poi.middleAddrName?.trim() ?: "") + " " +
                    (poi.lowerAddrName?.trim() ?: "") + " " +
                    (poi.detailAddrName?.trim() ?: "") + " " +
                    poi.firstNo?.trim()
        } else {
            (poi.upperAddrName?.trim() ?: "") + " " +
                    (poi.middleAddrName?.trim() ?: "") + " " +
                    (poi.lowerAddrName?.trim() ?: "") + " " +
                    (poi.detailAddrName?.trim() ?: "") + " " +
                    (poi.firstNo?.trim() ?: "") + "-" +
                    poi.secondNo?.trim()
        }

    private fun makeRoadName(poi: Poi): String =
        if (poi.secondBuildNo?.trim().isNullOrEmpty()) {
            (poi.upperAddrName?.trim() ?: "") + " " +
                    (poi.middleAddrName?.trim() ?: "") + " " +
                    (poi.roadName?.trim() ?: "") + " " +
                    poi.firstBuildNo?.trim()
        } else {
            (poi.upperAddrName?.trim() ?: "") + " " +
                    (poi.middleAddrName?.trim() ?: "") + " " +
                    (poi.roadName?.trim() ?: "") + " " +
                    (poi.firstBuildNo?.trim() ?: "") + "-" +
                    poi.secondBuildNo?.trim()
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}