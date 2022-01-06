package com.untilled.roadcapture.features.root.albums

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.untilled.roadcapture.data.dto.album.AlbumResponse
import com.untilled.roadcapture.data.dto.comment.Comments
import com.untilled.roadcapture.data.repository.album.AlbumCommentsPagingSource
import com.untilled.roadcapture.data.repository.album.AlbumRepository
import com.untilled.roadcapture.data.repository.album.PictureCommentsPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PictureViewerContainerViewModel
@Inject constructor(private val repository: AlbumRepository) : ViewModel() {
    private val _album = MutableLiveData<AlbumResponse>()
    val albumResponse: LiveData<AlbumResponse> get() = _album
    var currentPosition: Int = 0
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

    private fun getPictureCommentsResultStream(pictureId: Int): Flow<PagingData<Comments>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PictureCommentsPagingSource(repository,pictureId) }
        ).flow
    }

    fun getPictureComments(pictureId: Int): Flow<PagingData<Comments>> {
        return getPictureCommentsResultStream(pictureId).cachedIn(viewModelScope)
    }

    private fun getAlbumCommentsResultStream(albumId: Int): Flow<PagingData<Comments>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { AlbumCommentsPagingSource(repository,albumId) }
        ).flow
    }

    fun getAlbumComments(albumId: Int): Flow<PagingData<Comments>> {
        return getAlbumCommentsResultStream(albumId).cachedIn(viewModelScope)
    }
}

