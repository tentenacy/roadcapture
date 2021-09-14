package com.untilled.roadcapture.features.root.capture

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.untilled.roadcapture.searchPlaceResult
import com.untilled.roadcapture.utils.RetrofitBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class SearchPlaceFragment : Fragment(), CoroutineScope {
    private var _binding : FragmentSearchPlaceBinding? = null
    private val binding get() = _binding!!

    private var picture : Picture? = null

    private var resultList = listOf<SearchResult>()

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
            if(picture?.searchResult != null) {
                binding.edittextSearchPlaceInput.setText(picture?.searchResult?.placeName)
                searchKeyword(binding.edittextSearchPlaceInput.text.toString())
            }
        }
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.imageviewSearchPlaceBack.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(SearchPlaceFragmentDirections.actionSearchPlaceFragmentToPictureEditorFragment(
                    picture = picture
                ))
        }
        binding.imageviewSearchPlaceSearch.setOnClickListener {
            searchKeyword(binding.edittextSearchPlaceInput.text.toString())
        }
    }

    private fun initAdapter() {
        binding.recyclerviewSearchPlace.withModels {
            resultList.forEachIndexed { index, searchResult ->
                searchPlaceResult {
                    id(index)
                    searchResult(searchResult)

                    onClickItem { model, parentView, clickedView, position ->
                        if(clickedView.id == R.id.item_search_place_result_name_container){
                            picture?.searchResult = resultList[position]
                            Navigation.findNavController(binding.root)
                                .navigate(SearchPlaceFragmentDirections.actionSearchPlaceFragmentToPictureEditorFragment(
                                    picture = picture
                                ))
                        }
                    }
                }
            }
        }
    }
    private fun setData(pois : Pois) {
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

    private fun makeAddressNumber(poi : Poi) : String =
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

    private fun makeRoadName(poi : Poi) : String =
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

    private fun searchKeyword(keyword : String) {
        launch(coroutineContext) {
            try {
                withContext(Dispatchers.IO) {
                    val response = RetrofitBuilder.tmapApi.getSearchLocation(
                        keyword = keyword
                    )
                    if (response.isSuccessful) {
                        val body = response.body()
                        withContext(Dispatchers.Main) {
                            Log.e("List", body.toString())
                            body?.let {
                                setData(it.searchPoiInfo.pois)
                            }
                        }
                    }
                }
            } catch (e : Exception){
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