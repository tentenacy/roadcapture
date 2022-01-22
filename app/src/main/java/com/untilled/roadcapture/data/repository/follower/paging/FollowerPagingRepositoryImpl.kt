package com.untilled.roadcapture.data.repository.follower.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import com.untilled.roadcapture.data.datasource.api.dto.album.FollowersCondition
import com.untilled.roadcapture.data.datasource.paging.follower.FollowersPagingSource
import com.untilled.roadcapture.data.datasource.paging.follower.FollowingsPagingSource
import com.untilled.roadcapture.data.entity.paging.Followers
import com.untilled.roadcapture.data.entity.paging.Followings
import io.reactivex.rxjava3.core.Flowable

class FollowerPagingRepositoryImpl(
    private val followersPagingSource: FollowersPagingSource,
    private val followingsPagingSource: FollowingsPagingSource,
): FollowerPagingRepository {

    override fun getFollowers(userId: Long, followersCondition: FollowersCondition?): Flowable<PagingData<Followers.Follower>> {

        followersPagingSource.userId = userId
        followersPagingSource.followersCondition = followersCondition
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                maxSize = 30,
                prefetchDistance = 5,
                initialLoadSize = 40
            ),
            pagingSourceFactory = { followersPagingSource }
        ).flowable
    }

    override fun getFollowings(
        userId: Long,
        followingsCondition: FollowersCondition?
    ): Flowable<PagingData<Followings.Following>> {

        followersPagingSource.userId = userId
        followersPagingSource.followersCondition = followingsCondition
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                maxSize = 30,
                prefetchDistance = 5,
                initialLoadSize = 40
            ),
            pagingSourceFactory = { followingsPagingSource }
        ).flowable
    }
}