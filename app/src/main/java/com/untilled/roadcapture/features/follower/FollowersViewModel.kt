package com.untilled.roadcapture.features.follower

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.orhanobut.logger.Logger
import com.untilled.roadcapture.data.datasource.api.dto.user.FollowersCondition
import com.untilled.roadcapture.data.entity.paging.Followers
import com.untilled.roadcapture.data.repository.follower.FollowRepository
import com.untilled.roadcapture.data.repository.follower.paging.FollowerPagingRepository
import com.untilled.roadcapture.features.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class FollowersViewModel @Inject constructor(
    private val followerPagingRepository: FollowerPagingRepository,
    private val followRepository: FollowRepository,
) : BaseViewModel() {

    private val _user = MutableLiveData<PagingData<Followers.Follower>>()
    val user: LiveData<PagingData<Followers.Follower>> get() = _user

    fun getUserFollower(userId: Long, followersCondition: FollowersCondition? = null){
        followerPagingRepository.getUserFollowers(userId, followersCondition)
            .observeOn(AndroidSchedulers.mainThread())
            .cachedIn(viewModelScope)
            .subscribe({ response->
                _user.postValue(response)
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