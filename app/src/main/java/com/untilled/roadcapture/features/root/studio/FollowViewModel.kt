package com.untilled.roadcapture.features.root.studio

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.untilled.roadcapture.data.datasource.api.dto.common.PageRequest
import com.untilled.roadcapture.data.datasource.api.dto.common.PageResponse
import com.untilled.roadcapture.data.datasource.api.dto.user.FollowingsCondition
import com.untilled.roadcapture.data.datasource.api.dto.user.UsersResponse
import com.untilled.roadcapture.data.repository.follow.FollowRepository
import com.untilled.roadcapture.data.repository.user.LocalUserRepository
import com.untilled.roadcapture.data.repository.user.UserRepository
import com.untilled.roadcapture.features.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class FollowViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val localUserRepository: LocalUserRepository,
    private val followRepository: FollowRepository
) : BaseViewModel() {

    private val _user = MutableLiveData<PageResponse<UsersResponse>>()
    val user: LiveData<PageResponse<UsersResponse>> get() = _user

    fun getUserFollowing(followingsCondition: FollowingsCondition, pageRequest: PageRequest){
        userRepository.getUserFollowing(followingsCondition,pageRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response->
                _user.postValue(response)
            },{ t ->
                Logger.d("test: $t")
            }).addTo(compositeDisposable)
    }

    fun getUserFollower(followingsCondition: FollowingsCondition, pageRequest: PageRequest){
        userRepository.getUserFollower(followingsCondition,pageRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response->
                _user.postValue(response)
            },{ t ->
                Logger.d("test: $t")
            }).addTo(compositeDisposable)
    }

    fun follow(id: Int){
        followRepository.follow(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            },{ t ->
                Logger.d("test: $t")
            })
    }
}