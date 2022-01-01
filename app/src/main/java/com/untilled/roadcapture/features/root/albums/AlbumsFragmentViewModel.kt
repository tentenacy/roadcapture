package com.untilled.roadcapture.features.root.albums

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.untilled.roadcapture.data.repository.albums.AlbumsRepository
import com.untilled.roadcapture.data.response.albums.AlbumsResponse
import com.untilled.roadcapture.data.response.albums.CommentsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AlbumsFragmentViewModel
@Inject constructor(private val repository: AlbumsRepository) : ViewModel() {
    private val _albums = MutableLiveData<AlbumsResponse>()
    private val _comments = MutableLiveData<CommentsResponse>()
    val albums: LiveData<AlbumsResponse> get() = _albums
    val comments: LiveData<CommentsResponse> get() = _comments
    init {
        getAlbums()
    }

    private fun getAlbums() {
        viewModelScope.launch {
            repository.getAlbumsList(0,10)?.let { albumsResponse ->
                if(albumsResponse.isSuccessful) {
                    _albums.postValue(albumsResponse.body())
                } else {
                    Log.d("AlbumsResponse", "error: ${albumsResponse.code()}")
                }
            }
        }
    }
    fun getComments(albumsId: String){
        viewModelScope.launch {
            repository.getCommentsList(albumsId).let { commentsResponse ->
                if(commentsResponse.isSuccessful){
                    _comments.postValue(commentsResponse.body())
                } else {
                    Log.d("CommentsResponse", "error: ${commentsResponse.code()}")
                }
            }
        }
    }
}