package com.untilled.roadcapture.features.root.capture

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.untilled.roadcapture.databinding.FragmentSearchBinding
import com.untilled.roadcapture.databinding.FragmentSearchPlaceBinding
import com.untilled.roadcapture.searchPlaceResult
import com.untilled.roadcapture.utils.DummyDataSet
import com.untilled.roadcapture.utils.RetrofitBuilder
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SearchPlaceFragment : Fragment(), CoroutineScope {
    private var _binding : FragmentSearchPlaceBinding? = null
    private val binding get() = _binding!!

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

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.imageviewSearchPlaceBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.imageviewSearchPlaceSearch.setOnClickListener {
            searchKeyword(binding.edittextSearchPlaceInput.text.toString())
        }
    }

    private fun initAdapter() {
        binding.recyclerviewSearchPlace.withModels {
            DummyDataSet.searchResult.forEachIndexed { index, searchResult ->
                searchPlaceResult {
                    id(index)
                    searchResult(searchResult)
                }
            }
        }
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