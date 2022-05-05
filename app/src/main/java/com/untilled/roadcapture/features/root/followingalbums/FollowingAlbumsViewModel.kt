package com.untilled.roadcapture.features.root.followingalbums

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.untilled.roadcapture.data.datasource.api.dto.album.FollowingAlbumsCondition
import com.untilled.roadcapture.data.entity.paging.Albums
import com.untilled.roadcapture.data.entity.paging.FollowingsSortByAlbum
import com.untilled.roadcapture.data.repository.album.AlbumRepository
import com.untilled.roadcapture.data.repository.album.paging.AlbumPagingRepository
import com.untilled.roadcapture.data.repository.follower.paging.FollowerPagingRepository
import com.untilled.roadcapture.features.base.BaseViewModel
import com.untilled.roadcapture.utils.combineLatestData
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

    private val _followingAlbums = MutableLiveData<PagingData<Albums.Album>>()

    private val _followingsSortByAlbum = MutableLiveData<PagingData<FollowingsSortByAlbum.FollowingSortByAlbum>>()

    val load = MediatorLiveData<Pair<PagingData<Albums.Album>, PagingData<FollowingsSortByAlbum.FollowingSortByAlbum>>?>()

    init {
        load.addSource(_followingAlbums) {
            load.value = combineLatestData(_followingAlbums, _followingsSortByAlbum)
        }
        load.addSource(_followingsSortByAlbum) {
            load.value = combineLatestData(_followingAlbums, _followingsSortByAlbum)
        }
        load.value = null
    }

    fun loadAll(cond: FollowingAlbumsCondition? = null) {
        followingsSortByAlbum()
        followingAlbums(cond)
    }

    private fun followingsSortByAlbum() {
        followingPagingRepository.getFollowingsSortByAlbum()
            .cachedIn(viewModelScope)
            .subscribe({ pagingData ->
                _followingsSortByAlbum.postValue(pagingData)
            }) { t ->

            }.addTo(compositeDisposable)
    }

    private fun followingAlbums(cond: FollowingAlbumsCondition? = null) {
        albumPagingRepository.getFollowingAlbums(cond)
            .cachedIn(viewModelScope)
            .subscribe({ pagingData ->
                _followingAlbums.postValue(pagingData)
            }) { t ->

            }.addTo(compositeDisposable)
    }

    fun likeAlbum(albumsId: Long) {
        albumRepository.likeAlbum(albumsId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, {

            })
    }

    fun unlikeAlbum(albumsId: Long) {
        albumRepository.unlikeAlbum(albumsId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, {

            })
    }

    override fun onCleared() {
        super.onCleared()
    }
}