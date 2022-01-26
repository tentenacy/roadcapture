package com.untilled.roadcapture.features.root.studio

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.untilled.roadcapture.data.datasource.api.dto.album.UserAlbumsCondition
import com.untilled.roadcapture.data.datasource.api.dto.user.StudioUserResponse
import com.untilled.roadcapture.data.entity.paging.UserAlbums
import com.untilled.roadcapture.data.repository.album.paging.AlbumPagingRepository
import com.untilled.roadcapture.data.repository.user.UserRepository
import com.untilled.roadcapture.features.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MyStudioViewModel @Inject constructor(
    private val albumPagingRepository: AlbumPagingRepository,
    private val userRepository: UserRepository
): BaseViewModel() {

    private var _myAlbums = MutableLiveData<PagingData<UserAlbums.UserAlbum>>()
    val myAlbums: LiveData<PagingData<UserAlbums.UserAlbum>> get() = _myAlbums

    private var _userInfo = MutableLiveData<StudioUserResponse>()
    val userInfo: LiveData<StudioUserResponse> get() = _userInfo

    fun getMyStudioAlbums(cond: UserAlbumsCondition?) {
        albumPagingRepository.getMyStudioAlbums(cond)
            .subscribeOn(AndroidSchedulers.mainThread())
            .cachedIn(viewModelScope)
            .subscribe({
                _myAlbums.value = it
            }) { t ->

            }.addTo(compositeDisposable)
    }

    fun getMyInfo(){
        userRepository.getMyInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                _userInfo.value = response
            }) {

            }
    }

}