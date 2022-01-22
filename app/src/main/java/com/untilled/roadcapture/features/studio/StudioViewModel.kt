package com.untilled.roadcapture.features.studio

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.untilled.roadcapture.data.datasource.api.dto.common.PageRequest
import com.untilled.roadcapture.data.datasource.api.dto.common.PageResponse
import com.untilled.roadcapture.data.datasource.api.dto.address.PlaceCondition
import com.untilled.roadcapture.data.datasource.api.dto.album.UserAlbumsResponse
import com.untilled.roadcapture.data.datasource.api.dto.user.UsersResponse
import com.untilled.roadcapture.data.repository.follower.FollowRepository
import com.untilled.roadcapture.data.repository.user.UserRepository
import com.untilled.roadcapture.features.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class StudioViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val followRepository: FollowRepository
) : BaseViewModel() {

    private val _userInfo = MutableLiveData<UsersResponse>()
    val userInfo: LiveData<UsersResponse> get() = _userInfo

    private val _userAlbums = MutableLiveData<PageResponse<UserAlbumsResponse>>()
    val userAlbums : LiveData<PageResponse<UserAlbumsResponse>> get() = _userAlbums

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


    fun getUserAlbums(pageRequest: PageRequest, placeCondition: PlaceCondition){
        userRepository.getUserAlbums(pageRequest,placeCondition)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response->
                _userAlbums.postValue(response)
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

