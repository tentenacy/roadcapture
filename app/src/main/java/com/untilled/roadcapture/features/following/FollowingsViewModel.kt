package com.untilled.roadcapture.features.following

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.orhanobut.logger.Logger
import com.untilled.roadcapture.data.datasource.api.dto.follower.FollowingsCondition
import com.untilled.roadcapture.data.entity.paging.Followings
import com.untilled.roadcapture.data.repository.follower.FollowRepository
import com.untilled.roadcapture.data.repository.follower.paging.FollowerPagingRepository
import com.untilled.roadcapture.features.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class FollowingsViewModel @Inject constructor(
    private val followingPagingRepository: FollowerPagingRepository,
    private val followRepository: FollowRepository,
): BaseViewModel(){
    private val _user = MutableLiveData<PagingData<Followings.Following>>()
    val user: LiveData<PagingData<Followings.Following>> get() = _user

    fun getUserFollowing(userId: Long, followingsCondition: FollowingsCondition? = null){
        followingPagingRepository.getUserFollowings(userId, followingsCondition)
            .observeOn(AndroidSchedulers.mainThread())
            .cachedIn(viewModelScope)
            .subscribe({ response->
                _user.value = response
            },{ t ->
                Logger.d("test: $t")
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
}