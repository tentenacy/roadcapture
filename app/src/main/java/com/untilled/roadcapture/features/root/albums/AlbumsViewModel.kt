package com.untilled.roadcapture.features.root.albums

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import androidx.paging.rxjava3.cachedIn
import com.orhanobut.logger.Logger
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsCondition
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsResponse
import com.untilled.roadcapture.data.datasource.api.dto.comment.Comments
import com.untilled.roadcapture.data.datasource.api.dto.common.PageRequest
import com.untilled.roadcapture.data.datasource.api.dto.common.PageResponse
import com.untilled.roadcapture.data.datasource.api.dto.user.FollowingsCondition
import com.untilled.roadcapture.data.datasource.api.dto.user.UsersResponse
import com.untilled.roadcapture.data.entity.AlbumsPage

import com.untilled.roadcapture.data.repository.album.AlbumCommentsPagingSource
import com.untilled.roadcapture.data.repository.album.AlbumRepository
import com.untilled.roadcapture.data.repository.album.AlbumPagingRepository
import com.untilled.roadcapture.data.repository.follow.FollowRepository
import com.untilled.roadcapture.data.repository.user.UserRepository
import com.untilled.roadcapture.features.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@HiltViewModel
class AlbumsViewModel
@Inject constructor(
    private val albumRepository: AlbumRepository,
    private val userRepository: UserRepository,
    private val followRepository: FollowRepository,
    private val albumPagingRepository: AlbumPagingRepository,
) : BaseViewModel() {

    private var currentDateTimeFrom: String? = null
    private var currentDateTimeTo: String? = null
    private var currentAlbumsResult: Flow<PagingData<AlbumsResponse>>? = null

    private var _albums = MutableLiveData<PagingData<AlbumsPage.Albums>>()
    val albums: LiveData<PagingData<AlbumsPage.Albums>> get() = _albums


    //TODO 페이징으로 변경
    private val _user =  MutableLiveData<PageResponse<UsersResponse>>()
    val user: LiveData<PageResponse<UsersResponse>> get() = _user

    private val _followingAlbums = MutableLiveData<PageResponse<AlbumsResponse>>()
    val followingAlbums: LiveData<PageResponse<AlbumsResponse>> get() = _followingAlbums

    fun getAlbums(cond: AlbumsCondition) {
        albumPagingRepository.getAlbums(cond)
            .subscribeOn(AndroidSchedulers.mainThread())
            .cachedIn(viewModelScope)
            .subscribe({
                _albums.value = it
            }) { t ->

            }.addTo(compositeDisposable)
    }

    fun getAlbumComments(albumId: Int): Flow<PagingData<Comments>> {
        return getAlbumCommentsResultStream(albumId).cachedIn(viewModelScope)
    }

    private fun getAlbumCommentsResultStream(albumId: Int): Flow<PagingData<Comments>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { AlbumCommentsPagingSource(albumRepository,albumId) }
        ).flow
    }

    fun getUserFollowing(followingsCondition: FollowingsCondition, pageRequest: PageRequest){
        userRepository.getUserFollowing(followingsCondition, pageRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ user ->
                _user.postValue(user)
            },{ error ->
                Logger.d("test: $error")
            })
    }

    fun getFollowingAlbums(id: Int?, pageRequest: PageRequest){
        followRepository.getFollowingAlbums(id,pageRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                _followingAlbums.postValue(response)
            }, { t ->

            }).addTo(compositeDisposable)
    }

}