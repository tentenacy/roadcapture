package com.untilled.roadcapture.features.comment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.orhanobut.logger.Logger
import com.untilled.roadcapture.data.datasource.api.dto.comment.CommentCreateRequest
import com.untilled.roadcapture.data.entity.paging.AlbumComments
import com.untilled.roadcapture.data.repository.comment.CommentRepository
import com.untilled.roadcapture.data.repository.comment.paging.CommentPagingRepository
import com.untilled.roadcapture.features.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor(
    private val commentPagingRepository: CommentPagingRepository,
    private val commentRepository: CommentRepository,
): BaseViewModel() {

    companion object {
        const val EVENT_REFRESH = 1000
    }

    private var _albumComments = MutableLiveData<PagingData<AlbumComments.AlbumComment>>()
    val albumComments: LiveData<PagingData<AlbumComments.AlbumComment>> get() = _albumComments

    fun albumComments(albumId: Long) {
        commentPagingRepository.getAlbumComments(albumId)
            .observeOn(AndroidSchedulers.mainThread())
            .cachedIn(viewModelScope)
            .subscribe({
                _albumComments.postValue(it)
            }) { t ->
                Logger.e("${t}")
            }.addTo(compositeDisposable)
    }

    fun delete(commentId: Long) {
        commentRepository.deleteComment(commentId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewEvent(Pair(EVENT_REFRESH, Unit))
            }) { t ->
                Logger.e("${t}")
            }.addTo(compositeDisposable)
    }

    fun post(thumbnailPictureId: Long, request: CommentCreateRequest) {
        commentRepository.postPictureComment(thumbnailPictureId, request)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewEvent(Pair(EVENT_REFRESH, Unit))
            }) { t ->
                Logger.e("${t}")
            }.addTo(compositeDisposable)
    }
}