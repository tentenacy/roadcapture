package com.untilled.roadcapture.features.root.albums

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import androidx.paging.rxjava3.cachedIn
import com.orhanobut.logger.Logger
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsCondition
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsResponse
import com.untilled.roadcapture.data.datasource.api.dto.common.PageRequest
import com.untilled.roadcapture.data.datasource.api.dto.common.PageResponse
import com.untilled.roadcapture.data.datasource.api.dto.user.FollowingsCondition
import com.untilled.roadcapture.data.datasource.api.dto.user.UsersResponse
import com.untilled.roadcapture.data.entity.paging.Albums

import com.untilled.roadcapture.data.repository.album.paging.AlbumPagingRepository
import com.untilled.roadcapture.data.repository.follower.FollowRepository
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
    private val userRepository: UserRepository,
    private val followRepository: FollowRepository,
    private val albumPagingRepository: AlbumPagingRepository,
) : BaseViewModel() {

    private var currentDateTimeFrom: String? = null
    private var currentDateTimeTo: String? = null
    private var currentAlbumsResult: Flow<PagingData<AlbumsResponse>>? = null

    private var _albums = MutableLiveData<PagingData<Albums.Album>>()
    val album: LiveData<PagingData<Albums.Album>> get() = _albums

    private val _user =  MutableLiveData<PageResponse<UsersResponse>>()
    val user: LiveData<PageResponse<UsersResponse>> get() = _user

    private val _followingAlbums = MutableLiveData<PageResponse<AlbumsResponse>>()
    val followingAlbums: LiveData<PageResponse<AlbumsResponse>> get() = _followingAlbums

    fun getAlbums(cond: AlbumsCondition) {
        albumPagingRepository.getAlbums(cond)
            .subscribeOn(AndroidSchedulers.mainThread())
            .cachedIn(viewModelScope)
            .subscribe({ pagingData ->
                _albums.value = pagingData
            }) { t ->

            }.addTo(compositeDisposable)
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

    fun getFollowingAlbums(id: Long?, pageRequest: PageRequest){
        followRepository.getFollowingAlbums(id,pageRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                _followingAlbums.postValue(response)
            }, { t ->

            }).addTo(compositeDisposable)
    }

}