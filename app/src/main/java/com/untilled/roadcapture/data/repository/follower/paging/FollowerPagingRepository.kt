package com.untilled.roadcapture.data.repository.follower.paging

import androidx.paging.PagingData
import com.untilled.roadcapture.data.datasource.api.dto.user.FollowingsCondition
import com.untilled.roadcapture.data.datasource.api.dto.user.FollowersCondition
import com.untilled.roadcapture.data.entity.paging.*
import io.reactivex.rxjava3.core.Flowable

interface FollowerPagingRepository {

    fun getFollowers(
        userId: Long,
        followersCondition: FollowersCondition?,
    ): Flowable<PagingData<Followers.Follower>>

    fun getFollowings(
        userId: Long,
        followingsCondition: FollowingsCondition?,
    ): Flowable<PagingData<Followings.Following>>

}