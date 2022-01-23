package com.untilled.roadcapture.data.datasource.paging.follower

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.untilled.roadcapture.data.datasource.api.RoadCaptureApi
import com.untilled.roadcapture.data.datasource.api.dto.user.FollowersCondition
import com.untilled.roadcapture.data.entity.mapper.FollowersMapper
import com.untilled.roadcapture.data.entity.paging.Followers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.properties.Delegates

@Singleton
class FollowersPagingSource @Inject constructor(
    private val mapper: FollowersMapper,
    private val roadCaptureApi: RoadCaptureApi,
): RxPagingSource<Int, Followers.Follower>() {

    var userId: Long? = null
    var followersCondition: FollowersCondition? = null

    override fun getRefreshKey(state: PagingState<Int, Followers.Follower>): Int? {
        return null
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Followers.Follower>> {
        val position = params.key ?: 0

        return roadCaptureApi.getUserFollowers(
            page = position,
            size = params.loadSize,
            id = userId,
            username = followersCondition?.username,
        )
            .subscribeOn(Schedulers.io())
            .map { mapper.transformToFollowers(it) }
            .map { toLoadResult(it, position) }
            .onErrorReturn { LoadResult.Error(it) }
    }

    private fun toLoadResult(data: Followers, position: Int): LoadResult<Int, Followers.Follower> {
        return LoadResult.Page(
            data = data.followers,
            prevKey = if(position == 0) null else position - 1,
            nextKey = if(position == data.total - 1) null else position + 1,
        )
    }
}