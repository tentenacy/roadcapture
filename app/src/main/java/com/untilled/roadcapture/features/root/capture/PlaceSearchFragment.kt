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
import androidx.navigation.fragment.navArgs
import com.untilled.roadcapture.data.datasource.api.dto.address.Address
import com.untilled.roadcapture.data.datasource.api.dto.place.PlaceRequest
import com.untilled.roadcapture.data.datasource.api.dto.place.SearchPlaceResponse
import com.untilled.roadcapture.data.datasource.api.dto.poi.Poi
import com.untilled.roadcapture.data.datasource.api.dto.poi.Pois
import com.untilled.roadcapture.data.entity.Picture
import com.untilled.roadcapture.databinding.FragmentPlaceSearchBinding
import com.untilled.roadcapture.features.common.CustomDivider
import com.untilled.roadcapture.utils.hideKeyboard
import com.untilled.roadcapture.utils.navigateToCapture
import com.untilled.roadcapture.utils.navigateToPictureEditor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class PlaceSearchFragment : Fragment() {
    private var _binding: FragmentPlaceSearchBinding? = null
    val binding get() = _binding!!

    private val searchViewModel: PlaceSearchViewModel by viewModels()

    private lateinit var picture: Picture

    private val adapter: PlaceSearchAdapter by lazy {
        PlaceSearchAdapter(itemOnClickListener)
    }

    @Inject
    lateinit var customDivider: CustomDivider

    private val itemOnClickListener: (PlaceRequest?) -> Unit = { placeRequest ->
        picture.place = placeRequest
        navigateToCapture(picture)
    }

    private val placeSearchObserver: (SearchPlaceResponse?) -> Unit = { searchPlaceResponse ->
        adapter.run {
            binding.progressbarPlaceSearchLoading.isVisible = false // 로딩 애니메이션 off
            if (searchPlaceResponse != null) {
                placeList = poisToPlace(searchPlaceResponse.searchPoiInfo.pois)
                notifyDataSetChanged()
            } else {
                displayNoResult()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlaceSearchBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        observeData()
        getNavArgs()
        setOnClickListeners()
    }

    private fun initAdapter() {
        binding.recyclerPlaceSearch.addItemDecoration(customDivider)
        binding.recyclerPlaceSearch.adapter = adapter
    }

    private fun observeData() {
        searchViewModel.searchPlaceResponse.observe(viewLifecycleOwner, placeSearchObserver)
    }

    private fun getNavArgs() {
        val args: PlaceSearchFragmentArgs by navArgs()
        if (args.picture != null) {
            picture = args.picture!!
            picture.place?.name?.let {
                binding.edtPlaceSearchInput.setText(it)

                displayLoadingAnimation()
                searchViewModel.getSearchPlace(it)
            }
        }
    }

    private fun setOnClickListeners() {
        binding.imagePlaceSearchBack.setOnClickListener {
            navigateToPictureEditor(picture)
        }

        binding.edtPlaceSearchInput.setOnEditorActionListener { v, actionId, event ->
            when (actionId) {
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

    private fun poisToPlace(pois: Pois): List<PlaceRequest> =
        pois.poi.map {
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

    private fun displayNoResult() {
        binding.textPlaceSearchNoresult.isVisible = true
        adapter.clear()
        adapter.notifyDataSetChanged()
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