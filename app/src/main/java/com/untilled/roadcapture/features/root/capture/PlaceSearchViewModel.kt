package com.untilled.roadcapture.features.root.capture

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.untilled.roadcapture.data.dto.place.SearchPlaceResponse
import com.untilled.roadcapture.data.repository.place.SearchPlaceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaceSearchViewModel
@Inject constructor(private val repository: SearchPlaceRepository) : ViewModel() {
    private val _searchPlaceResponse = MutableLiveData<SearchPlaceResponse>()
    val searchPlaceResponse: LiveData<SearchPlaceResponse> get() = _searchPlaceResponse

    fun getSearchPlace(keyword: String) {
        viewModelScope.launch {
            repository.getSearchLocation(keyword= keyword).let { searchPlaceResponse ->
                searchPlaceResponse
                if(searchPlaceResponse.isSuccessful) {
                    _searchPlaceResponse.postValue(searchPlaceResponse.body())
                } else {
                    Log.d("SearchPlaceResponse", "error: ${searchPlaceResponse.code()}")
                }
            }
        }
    }
}