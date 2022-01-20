package com.untilled.roadcapture.features.root.studio

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.orhanobut.logger.Logger
import com.untilled.roadcapture.data.datasource.api.dto.album.FollowersCondition
import com.untilled.roadcapture.data.datasource.api.dto.common.PageResponse
import com.untilled.roadcapture.data.datasource.api.dto.user.UsersResponse
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
class FollowerViewModel @Inject constructor(
    private val followerPagingRepository: FollowerPagingRepository,
    private val followRepository: FollowRepository,
) : BaseViewModel() {

    private val _user = MutableLiveData<PagingData<Followers.Follower>>()
    val user: LiveData<PagingData<Followers.Follower>> get() = _user

/*
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
*/

    fun getUserFollower(userId: Long, followersCondition: FollowersCondition? = null){
        followerPagingRepository.getFollowers(userId, followersCondition)
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