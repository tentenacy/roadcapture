package com.untilled.roadcapture.features.root.studio

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.untilled.roadcapture.data.datasource.api.dto.album.FollowersCondition
import com.untilled.roadcapture.data.entity.paging.Followings
import com.untilled.roadcapture.data.repository.follower.paging.FollowerPagingRepository
import com.untilled.roadcapture.features.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject

@HiltViewModel
class FollowingsViewModel @Inject constructor(
    private val followersPagingRepository: FollowerPagingRepository
): BaseViewModel() {

    private var _followings = MutableLiveData<PagingData<Followings.Following>>()
    val followings: LiveData<PagingData<Followings.Following>> get() = _followings

    fun getFollowings(userId: Long, cond: FollowersCondition? = null) {
        followersPagingRepository.getFollowings(userId, cond)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ pagingData ->

            }) { t ->

            }
    }
}