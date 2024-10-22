package com.untilled.roadcapture.data.repository.follower.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import com.untilled.roadcapture.data.datasource.api.dto.follower.FollowingsCondition
import com.untilled.roadcapture.data.datasource.api.dto.follower.FollowersCondition
import com.untilled.roadcapture.data.datasource.paging.follower.FollowersPagingSource
import com.untilled.roadcapture.data.datasource.paging.follower.FollowingsPagingSource
import com.untilled.roadcapture.data.datasource.paging.follower.FollowingsSortByAlbumPagingSource
import com.untilled.roadcapture.data.entity.paging.Followers
import com.untilled.roadcapture.data.entity.paging.Followings
import com.untilled.roadcapture.data.entity.paging.FollowingsSortByAlbum
import io.reactivex.rxjava3.core.Flowable

class FollowerPagingRepositoryImpl(
    private val followersPagingSource: FollowersPagingSource,
    private val followingsPagingSource: FollowingsPagingSource,
    private val followingsSortByAlbumPagingSource: FollowingsSortByAlbumPagingSource,
): FollowerPagingRepository {

    override fun getUserFollowers(userId: Long, followersCondition: FollowersCondition?): Flowable<PagingData<Followers.Follower>> {

        followersPagingSource.userId = userId
        followersPagingSource.followersCondition = followersCondition
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                prefetchDistance = 5,
                initialLoadSize = 40
            ),
            pagingSourceFactory = { followersPagingSource }
        ).flowable
    }

    override fun getUserFollowings(
        userId: Long,
        followingsCondition: FollowingsCondition?
    ): Flowable<PagingData<Followings.Following>> {

        followingsPagingSource.userId = userId
        followingsPagingSource.followingsCondition = followingsCondition
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                prefetchDistance = 5,
                initialLoadSize = 40
            ),
            pagingSourceFactory = { followingsPagingSource }
        ).flowable
    }

    override fun getFollowers(followersCondition: FollowersCondition?): Flowable<PagingData<Followers.Follower>> {
        followersPagingSource.userId = null
        followersPagingSource.followersCondition = followersCondition
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                prefetchDistance = 5,
                initialLoadSize = 40
            ),
            pagingSourceFactory = { followersPagingSource }
        ).flowable
    }

    override fun getFollowings(followingsCondition: FollowingsCondition?): Flowable<PagingData<Followings.Following>> {
        followingsPagingSource.userId = null
        followingsPagingSource.followingsCondition = followingsCondition
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                prefetchDistance = 5,
                initialLoadSize = 40
            ),
            pagingSourceFactory = { followingsPagingSource }
        ).flowable
    }

    override fun getFollowingsSortByAlbum(): Flowable<PagingData<FollowingsSortByAlbum.FollowingSortByAlbum>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                prefetchDistance = 5,
                initialLoadSize = 40
            ),
            pagingSourceFactory = { followingsSortByAlbumPagingSource }
        ).flowable
    }
}