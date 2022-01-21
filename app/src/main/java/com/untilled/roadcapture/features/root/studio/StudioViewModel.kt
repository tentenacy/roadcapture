package com.untilled.roadcapture.features.root.studio

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.untilled.roadcapture.data.datasource.api.dto.common.PageRequest
import com.untilled.roadcapture.data.datasource.api.dto.common.PageResponse
import com.untilled.roadcapture.data.datasource.api.dto.address.AddressRequest
import com.untilled.roadcapture.data.datasource.api.dto.user.FollowingsCondition
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

    private val _user = MutableLiveData<UsersResponse>()
    val user: LiveData<UsersResponse> get() = _user

    private val _albums = MutableLiveData<PageResponse<UserAlbumsResponse>>()
    val albums : LiveData<PageResponse<UserAlbumsResponse>> get() = _albums

    private val _follower = MutableLiveData<PageResponse<UsersResponse>>()
    val follower : LiveData<PageResponse<UsersResponse>> get() = _follower

    private val _following = MutableLiveData<PageResponse<UsersResponse>>()
    val following : LiveData<PageResponse<UsersResponse>> get() = _following

    fun getUserInfo(id: Long){
        userRepository.getUserInfo(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ userResponse ->
                _user.postValue(userResponse)
            },{ t ->
                Logger.d("test: $t")
            }).addTo(compositeDisposable)
    }

    fun getUserFollowing(followingsCondition: FollowingsCondition, pageRequest: PageRequest){
        userRepository.getUserFollowing(followingsCondition,pageRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response->
                _following.postValue(response)
            },{ t ->
                Logger.d("test: $t")
            }).addTo(compositeDisposable)
    }

    fun getUserAlbums(pageRequest: PageRequest, addressRequest: AddressRequest){
        userRepository.getUserAlbums(pageRequest,addressRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response->
                _albums.postValue(response)
            },{

            }).addTo(compositeDisposable)
    }

    fun getUserFollower(followingsCondition: FollowingsCondition, pageRequest: PageRequest){
        userRepository.getUserFollower(followingsCondition,pageRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response->
                _follower.postValue(response)
            },{ t ->
                Logger.d("test: $t")
            }).addTo(compositeDisposable)
    }

    fun follow(id: Long){
        followRepository.follow(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            },{ t ->
                Logger.d("test: $t")
            })
    }

}

