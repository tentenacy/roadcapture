package com.untilled.roadcapture.features.following

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.orhanobut.logger.Logger
import com.untilled.roadcapture.data.datasource.api.dto.album.FollowersCondition
import com.untilled.roadcapture.data.datasource.api.dto.user.FollowingsCondition
import com.untilled.roadcapture.data.entity.paging.Followers
import com.untilled.roadcapture.data.entity.paging.Followings
import com.untilled.roadcapture.data.repository.follower.paging.FollowerPagingRepository
import com.untilled.roadcapture.features.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

@HiltViewModel
class FollowingsViewModel @Inject constructor(
    private val followingPagingRepository: FollowerPagingRepository
): BaseViewModel(){
    private val _user = MutableLiveData<PagingData<Followings.Following>>()
    val user: LiveData<PagingData<Followings.Following>> get() = _user

    fun getUserFollowing(userId: Long, followingsCondition: FollowingsCondition? = null){
        followingPagingRepository.getFollowings(userId, followingsCondition)
            .observeOn(AndroidSchedulers.mainThread())
            .cachedIn(viewModelScope)
            .subscribe({ response->
                _user.value = response
            },{ t ->
                Logger.d("test: $t")
            }).addTo(compositeDisposable)
    }
}