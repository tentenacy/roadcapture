package com.untilled.roadcapture.features.comment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.untilled.roadcapture.data.datasource.api.dto.comment.CommentCreateRequest
import com.untilled.roadcapture.data.entity.paging.AlbumComments
import com.untilled.roadcapture.data.repository.comment.CommentRepository
import com.untilled.roadcapture.data.repository.comment.paging.CommentPagingRepository
import com.untilled.roadcapture.features.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor(
    private val commentPagingRepository: CommentPagingRepository,
): BaseViewModel() {

    private var _albumComments = MutableLiveData<PagingData<AlbumComments.AlbumComment>>()
    val albumComments: LiveData<PagingData<AlbumComments.AlbumComment>> get() = _albumComments

    var itemCount = MutableLiveData<Int>()

    fun getAlbumComments(albumId: Long) {
        commentPagingRepository.getAlbumComments(albumId)
            .subscribeOn(AndroidSchedulers.mainThread())
            .cachedIn(viewModelScope)
            .subscribe({
                _albumComments.value = it
            }) { t ->

            }.addTo(compositeDisposable)
    }
}