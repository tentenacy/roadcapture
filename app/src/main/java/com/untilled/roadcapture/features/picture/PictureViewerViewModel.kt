package com.untilled.roadcapture.features.picture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumResponse
import com.untilled.roadcapture.data.entity.paging.AlbumComments
import com.untilled.roadcapture.data.entity.paging.PictureComments
import com.untilled.roadcapture.data.repository.album.AlbumRepository
import com.untilled.roadcapture.data.repository.comment.paging.CommentPagingRepository
import com.untilled.roadcapture.features.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class PictureViewerViewModel @Inject constructor(
    private val albumRepository: AlbumRepository,
    private val commentPagingRepository: CommentPagingRepository) : BaseViewModel() {

    private val _album = MutableLiveData<AlbumResponse>()
    val album: LiveData<AlbumResponse> get() = _album
    var currentPosition: Int = 0

    private var _albumComments = MutableLiveData<PagingData<AlbumComments.AlbumComment>>()
    val albumComments: LiveData<PagingData<AlbumComments.AlbumComment>> get() = _albumComments

    private var _pictureComments = MutableLiveData<PagingData<PictureComments.PictureComment>>()
    val pictureComments: LiveData<PagingData<PictureComments.PictureComment>> get() = _pictureComments

    fun getAlbumDetail(id: Long) {
        albumRepository.getAlbumDetail(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                _album.postValue(response)
            },{

            }).addTo(compositeDisposable)
    }

    fun getAlbumComments(albumId: Long) {
        commentPagingRepository.getAlbumComments(albumId)
            .subscribeOn(AndroidSchedulers.mainThread())
            .cachedIn(viewModelScope)
            .subscribe({
                _albumComments.value = it
            }) { t ->

            }.addTo(compositeDisposable)
    }

    fun getPictureComments(pictureId: Long) {
        commentPagingRepository.getPictureComments(pictureId)
            .subscribeOn(AndroidSchedulers.mainThread())
            .cachedIn(viewModelScope)
            .subscribe({
                _pictureComments.value = it
            }) { t ->

            }.addTo(compositeDisposable)
    }


/*
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
*/

/*
    fun getPictureComments(pictureId: Int): Flow<PagingData<Comments>> {
        return getPictureCommentsResultStream(pictureId).cachedIn(viewModelScope)
    }
*/

/*
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
*/

/*
    fun getAlbumComments(albumId: Int): Flow<PagingData<Comments>> {
        return getAlbumCommentsResultStream(albumId).cachedIn(viewModelScope)
    }
*/
}
