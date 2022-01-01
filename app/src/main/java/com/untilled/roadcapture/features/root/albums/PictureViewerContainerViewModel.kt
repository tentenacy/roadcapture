package com.untilled.roadcapture.features.root.albums

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.untilled.roadcapture.data.entity.Album
import com.untilled.roadcapture.data.repository.albums.AlbumsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PictureViewerContainerViewModel
@Inject constructor(private val repository: AlbumsRepository) : ViewModel() {
    private val _album = MutableLiveData<Album>()
    val album: LiveData<Album> get() = _album
    lateinit var id: String


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

