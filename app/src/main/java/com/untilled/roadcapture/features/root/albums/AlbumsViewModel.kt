package com.untilled.roadcapture.features.root.albums

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.untilled.roadcapture.data.dto.album.Albums
import com.untilled.roadcapture.data.dto.comment.Comments
import com.untilled.roadcapture.data.repository.album.AlbumRepository
import com.untilled.roadcapture.data.repository.album.AlbumsPagingSource
import com.untilled.roadcapture.data.repository.album.AlbumCommentsPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@HiltViewModel
class AlbumsViewModel
@Inject constructor(private val repository: AlbumRepository) : ViewModel() {

    private var currentDateTimeFrom: String? = null
    private var currentDateTimeTo: String? = null
    private var currentAlbumsResult: Flow<PagingData<Albums>>? = null

    private fun getAlbumsResultStream(token: String,dateTimeFrom: String?, dateTimeTo: String?): Flow<PagingData<Albums>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { AlbumsPagingSource(repository,token,dateTimeFrom,dateTimeTo) }
        ).flow
    }

    fun getAlbums(token: String,dateTimeFrom: String?, dateTimeTo: String?): Flow<PagingData<Albums>>{
        val lastResult = currentAlbumsResult
        if(dateTimeFrom == currentDateTimeFrom && dateTimeTo == currentDateTimeTo && lastResult != null){
            return lastResult
        }
        currentDateTimeFrom = dateTimeFrom
        currentDateTimeTo = dateTimeTo
        val newResult: Flow<PagingData<Albums>> = getAlbumsResultStream(token,dateTimeFrom,dateTimeTo).cachedIn(viewModelScope)
        currentAlbumsResult = newResult
        return newResult
    }

    private fun getAlbumCommentsResultStream(token: String,albumId: Int): Flow<PagingData<Comments>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { AlbumCommentsPagingSource(repository,token,albumId) }
        ).flow
    }

    fun getAlbumComments(token: String,albumId: Int): Flow<PagingData<Comments>> {
        return getAlbumCommentsResultStream(token,albumId).cachedIn(viewModelScope)
    }

//    fun getAlbums(dateTimeFrom: String, dateTimeTo: String) {
//        viewModelScope.launch {
//            repository.getAlbumsList(0,10, dateTimeFrom, dateTimeTo)?.let { albumsResponse ->
//                Log.d("Test",albumsResponse.raw().request.url.toUrl().toString())
//                if(albumsResponse.isSuccessful) {
//                    albumsResponse.body()?.albums?.forEachIndexed { index, albums ->
//                        albums.createdAt = dateToSnsFormat(albums.createdAt)
//                    }
//                    _albumsResponse.postValue(albumsResponse.body())
//                } else {
//                    Log.d("AlbumsResponse", "error: ${albumsResponse.code()}")
//                }
//            }
//        }
//    }

//    fun getComments(albumId: String){
//        viewModelScope.launch {
//            repository.getCommentsList(albumId).let { commentsResponse ->
//                if(commentsResponse.isSuccessful){
//                    commentsResponse.body()?.comments?.forEachIndexed { index, comments ->
//                        comments.createdAt = dateToSnsFormat(comments.createdAt)
//                    }
//                    _comments.postValue(commentsResponse.body())
//                } else {
//                    Log.d("CommentsResponse", "error: ${commentsResponse.code()}")
//                }
//            }
//        }
//    }
}