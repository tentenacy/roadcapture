package com.untilled.roadcapture.features.root.albums

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.untilled.roadcapture.data.repository.albums.AlbumsRepository
import com.untilled.roadcapture.data.response.albums.AlbumsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AlbumsFragmentViewModel
@Inject constructor(private val repository: AlbumsRepository) : ViewModel() {
    private val _albums = MutableLiveData<AlbumsResponse>()
    val albums: LiveData<AlbumsResponse> get() = _albums

    init {
        getAlbums()
    }

    private fun getAlbums() {
        viewModelScope.launch {
            repository.getAlbumsList(0,10)?.let { albumsResponse ->
                if(albumsResponse.isSuccessful) {
                    _albums.postValue(albumsResponse.body())
                } else {
                    Log.d("response", "error: ${albumsResponse.code()}")
                }
            }
        }
    }
}