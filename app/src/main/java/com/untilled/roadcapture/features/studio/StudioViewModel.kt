package com.untilled.roadcapture.features.studio

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.orhanobut.logger.Logger
import com.untilled.roadcapture.data.datasource.api.dto.album.UserAlbumsCondition
import com.untilled.roadcapture.data.datasource.api.dto.user.StudioUserResponse
import com.untilled.roadcapture.data.entity.paging.UserAlbums
import com.untilled.roadcapture.data.repository.album.paging.AlbumPagingRepository
import com.untilled.roadcapture.data.repository.follower.FollowRepository
import com.untilled.roadcapture.data.repository.user.UserRepository
import com.untilled.roadcapture.features.base.BaseViewModel
import com.untilled.roadcapture.utils.combineLatestData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class StudioViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val albumPagingRepository: AlbumPagingRepository,
    private val followRepository: FollowRepository
) : BaseViewModel() {

    private val _userInfo = MutableLiveData<StudioUserResponse>()

    private var _albums = MutableLiveData<PagingData<UserAlbums.UserAlbum>>()

    val load = MediatorLiveData<Pair<StudioUserResponse, PagingData<UserAlbums.UserAlbum>>?>()

    init {
        load.addSource(_userInfo) {
            load.value = combineLatestData(_userInfo, _albums)
        }
        load.addSource(_albums) {
            load.value = combineLatestData(_userInfo, _albums)
        }
        load.value = null
    }

    fun loadAll(userId: Long?, cond: UserAlbumsCondition? = null) {
        getStudioAlbums(userId, cond)
        userId?.let { getUserInfo(it) }
    }

    fun getUserInfo(id: Long){
        userRepository.getUserInfo(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ userResponse ->
                _userInfo.postValue(userResponse)
            },{ t ->
                Logger.d("test: $t")
            }).addTo(compositeDisposable)
    }


    fun getStudioAlbums(userId: Long?, cond: UserAlbumsCondition?){
        albumPagingRepository.getStudioAlbums(userId, cond)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .cachedIn(viewModelScope)
            .subscribe({ response->
                _albums.postValue(response)
            },{

            }).addTo(compositeDisposable)
    }

    fun follow(toUserId: Long){
        followRepository.follow(toUserId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            },{ t ->
                Logger.d("test: $t")
            })
    }

    fun unfollow(toUserId: Long){
        followRepository.unfollow(toUserId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            },{ t ->
                Logger.d("test: $t")
            })
    }

}

