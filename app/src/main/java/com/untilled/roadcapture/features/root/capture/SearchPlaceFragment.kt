package com.untilled.roadcapture.features.root.capture

import android.content.Context.INPUT_METHOD_SERVICE
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.getSystemService
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.entity.LocationLatLng
import com.untilled.roadcapture.data.entity.Picture
import com.untilled.roadcapture.data.response.search.Pois
import com.untilled.roadcapture.data.entity.SearchResult
import com.untilled.roadcapture.data.response.search.Poi
import com.untilled.roadcapture.databinding.FragmentSearchPlaceBinding
import com.untilled.roadcapture.features.base.CustomDivider
import com.untilled.roadcapture.searchPlaceResult
import com.untilled.roadcapture.utils.RetrofitBuilder
import com.untilled.roadcapture.utils.extension.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class SearchPlaceFragment : Fragment(), CoroutineScope {
    private var _binding: FragmentSearchPlaceBinding? = null
    private val binding get() = _binding!!

    private var picture: Picture? = null

    private var resultList: List<SearchResult>? = listOf<SearchResult>()

    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchPlaceBinding.inflate(inflater, container, false)

        job = Job()
        initAdapter()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: SearchPlaceFragmentArgs by navArgs()
        if (args.picture != null) {
            picture = args.picture
            if (picture?.searchResult != null) {
                binding.edittextSearchPlaceInput.setText(picture?.searchResult?.placeName)
                searchKeyword(binding.edittextSearchPlaceInput.text.toString())
            }
        }
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.imageviewSearchPlaceBack.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(
                    SearchPlaceFragmentDirections.actionSearchPlaceFragmentToPictureEditorFragment(
                        picture = picture
                    )
                )
        }

        binding.edittextSearchPlaceInput.setOnEditorActionListener { v, actionId, event ->
            when(actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    (v as EditText).text.toString().run {
                        if (isNullOrBlank()) {
                            Toast.makeText(requireContext(), "검색어를 입력해 주세요.", Toast.LENGTH_SHORT).show()
                        } else {
                            searchKeyword(this)
                        }
                    }
                    requireActivity().hideKeyboard(binding.edittextSearchPlaceInput)
                    return@setOnEditorActionListener true
                }
                else -> return@setOnEditorActionListener false
            }
        }
    }

    private fun initAdapter() {
        val customDivider = CustomDivider(2.5f,1f, Color.parseColor("#EFEFEF"))

        binding.recyclerviewSearchPlace.addItemDecoration(customDivider)

        binding.recyclerviewSearchPlace.withModels {
            resultList?.forEachIndexed { index, searchResult ->
                searchPlaceResult {
                    id(index)
                    searchResult(searchResult)

                    onClickItem { model, parentView, clickedView, position ->
                        if (clickedView.id == R.id.item_search_place_result_name_container) {
                            picture?.searchResult = resultList!![position]
                            Navigation.findNavController(binding.root)
                                .navigate(
                                    SearchPlaceFragmentDirections.actionSearchPlaceFragmentToPictureEditorFragment(
                                        picture = picture
                                    )
                                )
                        }
                    }
                }
            }
        }
    }

    private fun setData(pois: Pois) {
        resultList = pois.poi.map {
            SearchResult(
                placeName = it.name ?: "",
                addressNumber = makeAddressNumber(it),
                roadName = makeRoadName(it),
                locationLatLng = LocationLatLng(it.noorLat, it.noorLon)
            )
        }
        binding.recyclerviewSearchPlace.requestModelBuild()
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

    private fun searchKeyword(keyword: String) {
        binding.progressbarSearchPlaceLoading.isVisible = true  // 로딩 애니메이션 on

        launch(coroutineContext) {
            try {
                withContext(Dispatchers.IO) {
                    val response = RetrofitBuilder.tmapApi.getSearchLocation(
                        keyword = keyword
                    )
                    if (response.isSuccessful) {
                        val body = response.body()

                        withContext(Dispatchers.Main) {
                            if (body == null) {  // 검색 결과 없을때 처리
                                binding.textviewSearchPlaceNoResult.isVisible = true
                                resultList = null   // 결과 리스트 초기화
                                binding.recyclerviewSearchPlace.requestModelBuild()
                            }
                            binding.progressbarSearchPlaceLoading.isVisible = false // 로딩 애니메이션 off

                            Log.d("searchResult", body.toString())
                            body?.let {
                                binding.textviewSearchPlaceNoResult.isVisible = false
                                setData(it.searchPoiInfo.pois)
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                binding.progressbarSearchPlaceLoading.isVisible = false
                e.printStackTrace()
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
        _binding = null
    }
}