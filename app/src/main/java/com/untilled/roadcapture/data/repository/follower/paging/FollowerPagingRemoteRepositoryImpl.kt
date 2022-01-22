package com.untilled.roadcapture.data.repository.follower.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import com.untilled.roadcapture.data.datasource.api.dto.album.FollowersCondition
import com.untilled.roadcapture.data.datasource.dao.paging.comment.AlbumCommentsDao
import com.untilled.roadcapture.data.datasource.dao.paging.comment.PictureCommentsDao
import com.untilled.roadcapture.data.datasource.dao.paging.follower.FollowersDao
import com.untilled.roadcapture.data.datasource.dao.paging.follower.FollowersRemoteKeysDao
import com.untilled.roadcapture.data.datasource.dao.paging.follower.FollowingsDao
import com.untilled.roadcapture.data.datasource.paging.comment.AlbumCommentsRemoteMediator
import com.untilled.roadcapture.data.datasource.paging.comment.PictureCommentsRemoteMediator
import com.untilled.roadcapture.data.datasource.paging.follower.FollowersRemoteMediator
import com.untilled.roadcapture.data.datasource.paging.follower.FollowingsRemoteMediator
import com.untilled.roadcapture.data.entity.paging.AlbumComments
import com.untilled.roadcapture.data.entity.paging.Followers
import com.untilled.roadcapture.data.entity.paging.Followings
import com.untilled.roadcapture.data.entity.paging.PictureComments
import io.reactivex.rxjava3.core.Flowable

@ExperimentalPagingApi
class FollowerPagingRemoteRepositoryImpl(
    private val followersDao: FollowersDao,
    private val followingsDao: FollowingsDao,
    private val followersRemoteMediator: FollowersRemoteMediator,
    private val followingsRemoteMediator: FollowingsRemoteMediator,
): FollowerPagingRepository {

    override fun getFollowers(
        userId: Long,
        followersCondition: FollowersCondition?
    ): Flowable<PagingData<Followers.Follower>> {
        followersRemoteMediator.userId = userId
        followersRemoteMediator.followersCondition = followersCondition
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                maxSize = 30,
                prefetchDistance = 5,
                initialLoadSize = 40
            ),
            remoteMediator = followersRemoteMediator,
            pagingSourceFactory = { followersDao.selectAll() }
        ).flowable
    }

    override fun getFollowings(
        userId: Long,
        followingsCondition: FollowersCondition?
    ): Flowable<PagingData<Followings.Following>> {
        followersRemoteMediator.userId = userId
        followersRemoteMediator.followersCondition = followingsCondition
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                maxSize = 30,
                prefetchDistance = 5,
                initialLoadSize = 40
            ),
            remoteMediator = followingsRemoteMediator,
            pagingSourceFactory = { followingsDao.selectAll() }
        ).flowable
    }
}