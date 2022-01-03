package com.untilled.roadcapture.features.root.albums

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.untilled.roadcapture.data.dto.album.AlbumResponse
import com.untilled.roadcapture.data.repository.album.AlbumRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PictureViewerContainerViewModel
@Inject constructor(private val repository: AlbumRepository) : ViewModel() {
    private val _album = MutableLiveData<AlbumResponse>()
    val albumResponse: LiveData<AlbumResponse> get() = _album

    fun getAlbumDetail(id: String) {
        viewModelScope.launch {
            repository.getAlbumDetail(id).let { album ->
                Log.d("testt", album.raw().request.url.toUrl().toString())
                if (album.isSuccessful) {
                    _album.postValue(album.body())
                } else {
                    Log.d("Album", "error: ${album.code()}")
                }
            }
        }
    }
}

