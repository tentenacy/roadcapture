package com.untilled.roadcapture.features.picture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumResponse
import com.untilled.roadcapture.data.datasource.api.dto.comment.CommentCreateRequest
import com.untilled.roadcapture.data.entity.paging.AlbumComments
import com.untilled.roadcapture.data.entity.paging.PictureComments
import com.untilled.roadcapture.data.repository.album.AlbumRepository
import com.untilled.roadcapture.data.repository.comment.CommentRepository
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
    private val commentPagingRepository: CommentPagingRepository,
    private val commentRepository: CommentRepository,
    ) : BaseViewModel() {

    private val _album = MutableLiveData<AlbumResponse>()
    val album: LiveData<AlbumResponse> get() = _album

    private val _currentPosition = MutableLiveData<Int>()
    val currentPosition: LiveData<Int> get() = _currentPosition

    private val _albumComments = MutableLiveData<PagingData<AlbumComments.AlbumComment>?>()
    val albumComments: LiveData<PagingData<AlbumComments.AlbumComment>?> get() = _albumComments

    private val _pictureComments = MutableLiveData<PagingData<PictureComments.PictureComment>?>()
    val pictureComments: LiveData<PagingData<PictureComments.PictureComment>?> get() = _pictureComments

    private val _likeCount = MutableLiveData<Int>()
    val likeCount: LiveData<Int> get() = _likeCount

    private val _commentCount = MutableLiveData<Int>()
    val commentCount: LiveData<Int> get() = _commentCount

    private val _liked = MutableLiveData<Boolean>()
    val liked: LiveData<Boolean> get() = _liked

    fun setCurrentPosition(position: Int) {
        _currentPosition.value = position
    }

    fun likeOrUnlike() {
        liked.value?.let { liked ->
            if (liked) {
                _likeCount.value = _likeCount.value?.minus(1)
                unlike()
            } else {
                _likeCount.value = _likeCount.value?.plus(1)
                like()
            }
        }
        _liked.value = _liked.value?.not()
    }

    private fun like() = album.value?.apply {
        albumRepository.likeAlbum(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, { t ->
                error.value = t.message
            })
    }

    private fun unlike() = album.value?.apply {
        albumRepository.unlikeAlbum(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, { t ->
                error.value = t.message
            })
    }

    fun getAlbumDetail(id: Long) {
        albumRepository.getAlbumDetail(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                _album.postValue(response)
                response.apply {
                    _likeCount.postValue(likeCount)
                    _commentCount.postValue(commentCount)
                    _liked.postValue(liked)
                }
            }, { t ->
                error.value = t.message
            }).addTo(compositeDisposable)
    }

    fun getComments() = album.value?.apply {
        currentPosition.value?.let { position ->
            clearComments()
            if (position == 0) {
                getAlbumComments()
            } else {
                getPictureComments(pictures[position - 1].id)
            }
        }
    }

    private fun getAlbumComments() = album.value?.apply {
        commentPagingRepository.getAlbumComments(id)
            .observeOn(AndroidSchedulers.mainThread())
            .cachedIn(viewModelScope)
            .subscribe({
                _albumComments.postValue(it)
            }) { t ->
                error.value = t.message
            }.addTo(compositeDisposable)
    }

    private fun getPictureComments(pictureId: Long) = album.value?.apply {
        commentPagingRepository.getPictureComments(pictureId)
            .subscribeOn(AndroidSchedulers.mainThread())
            .cachedIn(viewModelScope)
            .subscribe({
                _pictureComments.postValue(it)
            }) { t ->
                error.value = t.message
            }.addTo(compositeDisposable)
    }

    fun postComment(request: CommentCreateRequest) = album.value?.apply {
        currentPosition.value?.let { position ->
            if (position == 0) {
                /*commentRepository.postPictureComment(pictures[position - 1].id, request)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        getComments()
                    }) { t ->
                        error.value = t.message
                    }.addTo(compositeDisposable)*/
            } else {
                commentRepository.postPictureComment(pictures[position - 1].id, request)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        getPictureComments(pictures[position - 1].id)
                    }) { t ->
                        error.value = t.message
                    }.addTo(compositeDisposable)
            }
        }
    }

    fun clearComments() {
        _pictureComments.value = null
        _albumComments.value = null
    }
}