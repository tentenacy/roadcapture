package com.untilled.roadcapture.features.root.studio

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.untilled.roadcapture.data.datasource.api.dto.album.FollowingAlbumsCondition
import com.untilled.roadcapture.data.datasource.api.dto.album.UserAlbumsCondition
import com.untilled.roadcapture.data.datasource.api.dto.user.StudioUserResponse
import com.untilled.roadcapture.data.entity.paging.Albums
import com.untilled.roadcapture.data.entity.paging.FollowingsSortByAlbum
import com.untilled.roadcapture.data.entity.paging.UserAlbums
import com.untilled.roadcapture.data.repository.album.AlbumRepository
import com.untilled.roadcapture.data.repository.album.paging.AlbumPagingRepository
import com.untilled.roadcapture.data.repository.user.UserRepository
import com.untilled.roadcapture.features.base.BaseViewModel
import com.untilled.roadcapture.utils.combineLatestData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MyStudioViewModel @Inject constructor(
    private val albumPagingRepository: AlbumPagingRepository,
    private val userRepository: UserRepository,
    private val albumRepository: AlbumRepository
): BaseViewModel() {

    private var _myAlbums = MutableLiveData<PagingData<UserAlbums.UserAlbum>>()

    private var _userInfo = MutableLiveData<StudioUserResponse>()

    val load = MediatorLiveData<Pair<StudioUserResponse, PagingData<UserAlbums.UserAlbum>>?>()

    init {
        load.addSource(_userInfo) {
            load.value = combineLatestData(_userInfo, _myAlbums)
        }
        load.addSource(_myAlbums) {
            load.value = combineLatestData(_userInfo, _myAlbums)
        }
        load.value = null
    }

    fun loadAll(cond: UserAlbumsCondition? = null) {
        getMyStudioAlbums(cond)
        getMyInfo()
    }

    private fun getMyStudioAlbums(cond: UserAlbumsCondition?) {
        albumPagingRepository.getMyStudioAlbums(cond)
            .subscribeOn(AndroidSchedulers.mainThread())
            .cachedIn(viewModelScope)
            .subscribe({
                _myAlbums.postValue(it)
            }) { t ->

            }.addTo(compositeDisposable)
    }

    private fun getMyInfo(){
        userRepository.getMyInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                _userInfo.postValue(response)
            }) {

            }.addTo(compositeDisposable)
    }

    fun deleteAlbum(albumId: Long){
        albumRepository.deleteAlbum(albumId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            },{

            }).addTo(compositeDisposable)
    }

}