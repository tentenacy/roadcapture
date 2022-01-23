package com.untilled.roadcapture.features.root.followingalbums

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.orhanobut.logger.Logger
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsCondition
import com.untilled.roadcapture.data.datasource.api.dto.album.FollowingAlbumsCondition
import com.untilled.roadcapture.data.datasource.api.dto.user.FollowingsCondition
import com.untilled.roadcapture.data.entity.paging.Albums
import com.untilled.roadcapture.data.entity.paging.Followings
import com.untilled.roadcapture.data.repository.album.AlbumRepository
import com.untilled.roadcapture.data.repository.album.paging.AlbumPagingRepository
import com.untilled.roadcapture.data.repository.follower.paging.FollowerPagingRepository
import com.untilled.roadcapture.features.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class FollowingAlbumsViewModel @Inject constructor(
    private val albumPagingRepository: AlbumPagingRepository,
    private val albumRepository: AlbumRepository,
    private val followingPagingRepository: FollowerPagingRepository
): BaseViewModel() {
    private var _followingAlbums = MutableLiveData<PagingData<Albums.Album>>()
    val followingAlbums: LiveData<PagingData<Albums.Album>> get() = _followingAlbums

    private val _user = MutableLiveData<PagingData<Followings.Following>>()
    val user: LiveData<PagingData<Followings.Following>> get() = _user

    var select = MutableLiveData<Boolean>()

    fun getMyFollowings(followingsCondition: FollowingsCondition? = null){
        followingPagingRepository.getFollowings(followingsCondition)
            .observeOn(AndroidSchedulers.mainThread())
            .cachedIn(viewModelScope)
            .subscribe({ response->
                _user.value = response
            },{ t ->
                Logger.d("test: $t")
            }).addTo(compositeDisposable)
    }

    fun getFollowingAlbums(cond: FollowingAlbumsCondition) {
        albumPagingRepository.getFollowingAlbums(cond)
            .observeOn(AndroidSchedulers.mainThread())
            .cachedIn(viewModelScope)
            .subscribe({ pagingData ->
                _followingAlbums.value = pagingData
            }) { t ->

            }.addTo(compositeDisposable)
    }

    fun likesAlbum(albumsId: Long) {
        albumRepository.likesAlbum(albumsId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, {

            })
    }

    fun unlikesAlbum(albumsId: Long) {
        albumRepository.unlikesAlbum(albumsId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, {

            })
    }
}