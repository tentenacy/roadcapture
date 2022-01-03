package com.untilled.roadcapture.features.root.albums

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.untilled.roadcapture.data.repository.album.AlbumRepository
import com.untilled.roadcapture.data.dto.album.AlbumsResponse
import com.untilled.roadcapture.data.dto.comment.CommentsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AlbumsViewModel
@Inject constructor(private val repository: AlbumRepository) : ViewModel() {
    private val _albumsResponse = MutableLiveData<AlbumsResponse>()
    private val _comments = MutableLiveData<CommentsResponse>()
    val albumsResponse: LiveData<AlbumsResponse> get() = _albumsResponse
    val comments: LiveData<CommentsResponse> get() = _comments
    init {
        getAlbums()
    }

    private fun getAlbums() {
        viewModelScope.launch {
            repository.getAlbumsList(0,10)?.let { albumsResponse ->
                if(albumsResponse.isSuccessful) {
                    _albumsResponse.postValue(albumsResponse.body())
                } else {
                    Log.d("AlbumsResponse", "error: ${albumsResponse.code()}")
                }
            }
        }
    }

    fun getComments(albumId: String){
        viewModelScope.launch {
            repository.getCommentsList(albumId).let { commentsResponse ->
                Log.d("testt", commentsResponse.raw().request.url.toUrl().toString())
                if(commentsResponse.isSuccessful){
                    _comments.postValue(commentsResponse.body())
                } else {
                    Log.d("CommentsResponse", "error: ${commentsResponse.code()}")
                }
            }
        }
    }
}