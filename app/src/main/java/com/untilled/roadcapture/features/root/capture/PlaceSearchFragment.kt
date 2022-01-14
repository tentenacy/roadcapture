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
import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.api.dto.address.Address
import com.untilled.roadcapture.data.api.dto.place.Place
import com.untilled.roadcapture.data.api.dto.poi.Poi
import com.untilled.roadcapture.data.api.dto.poi.Pois
import com.untilled.roadcapture.data.entity.LocationLatLng
import com.untilled.roadcapture.data.entity.Picture
import com.untilled.roadcapture.data.entity.SearchResult
import com.untilled.roadcapture.databinding.FragmentPlaceSearchBinding
import com.untilled.roadcapture.features.base.CustomDivider
import com.untilled.roadcapture.placeSearch
import com.untilled.roadcapture.utils.extension.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class PlaceSearchFragment : Fragment() {
    private var _binding: FragmentPlaceSearchBinding? = null
    private val binding get() = _binding!!

    private val searchViewModel: PlaceSearchViewModel by viewModels()

    private lateinit var picture: Picture
    private var resultList: List<SearchResult>? = listOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlaceSearchBinding.inflate(inflater, container, false)

        initAdapter()

        searchViewModel.searchPlaceResponse.observe(viewLifecycleOwner) { searchPlaceResponse ->

            binding.progressbarPlaceSearchLoading.isVisible = false // 로딩 애니메이션 off

            if(searchPlaceResponse != null) {
                setData(searchPlaceResponse.searchPoiInfo.pois)
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
            when(actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    (v as EditText).text.toString().run {
                        if (isNullOrBlank()) {
                            Toast.makeText(requireContext(), "검색어를 입력해 주세요.", Toast.LENGTH_SHORT).show()
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
        val customDivider = CustomDivider(2.5f,1f, Color.parseColor("#EFEFEF"))

        binding.recyclerPlaceSearch.addItemDecoration(customDivider)

        binding.recyclerPlaceSearch.withModels {
            resultList?.forEachIndexed { index, searchResult ->
                placeSearch {
                    id(index)
                    searchResult(searchResult)

                    onClickItem { model, parentView, clickedView, position ->
                        if (clickedView.id == R.id.constraint_iplacesearch_container) {
                            updatePicture(resultList!![position])

                            Navigation.findNavController(binding.root)
                                .navigate(
                                    PlaceSearchFragmentDirections.actionSearchPlaceFragmentToPictureEditorFragment(
                                        picture = picture
                                    )
                                )
                        }
                    }
                }
            }
        }
    }

    private fun updatePicture(searchResult: SearchResult) {
        picture.place = Place(
            latitude = searchResult.locationLatLng.latitude.toString(),
            longitude = searchResult.locationLatLng.longitude.toString(),
            name = searchResult.name,
            address = Address(
                addressName = searchResult.addressName,
                roadAddressName = searchResult.roadAddressName,
                region1DepthName = searchResult.region1DepthName,
                region2DepthName = searchResult.region2DepthName,
                region3DepthName = searchResult.region3DepthName,
                zoneNo = searchResult.zoneNo
            ),
            id = 0
        )
    }

    private fun displayNoResult() {
        binding.textPlaceSearchNoresult.isVisible = true
        resultList = null   // 결과 리스트 초기화
        binding.recyclerPlaceSearch.requestModelBuild()
    }

    private fun displayLoadingAnimation() {
        binding.progressbarPlaceSearchLoading.isVisible = true  // 로딩 애니메이션 on
        binding.textPlaceSearchNoresult.isVisible = false
    }

    private fun setData(pois: Pois) {
        resultList = pois.poi.map {
            SearchResult(
                name = it.name ?: "",
                addressName = makeAddressNumber(it),
                roadAddressName = makeRoadName(it),
                locationLatLng = LocationLatLng(it.noorLat, it.noorLon),
                region1DepthName = it.upperAddrName ?: "",
                region2DepthName = it.middleAddrName ?: "",
                region3DepthName = it.lowerAddrName ?: "",
                zoneNo = ""
            )
        }
        binding.recyclerPlaceSearch.requestModelBuild()
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