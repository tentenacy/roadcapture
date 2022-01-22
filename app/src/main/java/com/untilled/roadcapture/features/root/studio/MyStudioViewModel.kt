package com.untilled.roadcapture.features.root.studio

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.untilled.roadcapture.data.datasource.api.dto.album.UserAlbumsCondition
import com.untilled.roadcapture.data.datasource.api.dto.user.UsersResponse
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

    private var _userAlbums = MutableLiveData<PagingData<UserAlbums.UserAlbum>>()
    val userAlbums: LiveData<PagingData<UserAlbums.UserAlbum>> get() = _userAlbums

    private var _userInfo = MutableLiveData<UsersResponse>()
    val userInfo: LiveData<UsersResponse> get() = _userInfo

    fun getUserAlbums(cond: UserAlbumsCondition? = null) {
        albumPagingRepository.getUserAlbums(cond)
            .subscribeOn(AndroidSchedulers.mainThread())
            .cachedIn(viewModelScope)
            .subscribe({
                _userAlbums.value = it
            }) { t ->

            }.addTo(compositeDisposable)
    }

    fun getUserInfo(userId: Long){
        userRepository.getUserInfo(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                _userInfo.postValue(response)
            },{

            })
    }

}