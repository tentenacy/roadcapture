package com.untilled.roadcapture.data.datasource.paging.follower

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.untilled.roadcapture.data.datasource.api.RoadCaptureApi
import com.untilled.roadcapture.data.datasource.api.dto.user.FollowingsCondition
import com.untilled.roadcapture.data.entity.mapper.FollowersMapper
import com.untilled.roadcapture.data.entity.paging.Followings
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.properties.Delegates

@Singleton
class FollowingsPagingSource @Inject constructor(
    private val mapper: FollowersMapper,
    private val roadCaptureApi: RoadCaptureApi,
): RxPagingSource<Int, Followings.Following>() {

    var userId by Delegates.notNull<Long>()
    var followingsCondition: FollowingsCondition? = null

    override fun getRefreshKey(state: PagingState<Int, Followings.Following>): Int? {
        return null
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Followings.Following>> {
        val position = params.key ?: 0

        return roadCaptureApi.getUserFollowings(
            page = position,
            size = params.loadSize,
            id = userId,
            username = followingsCondition?.username,
        )
            .subscribeOn(Schedulers.io())
            .map { mapper.transformToFollowings(it) }
            .map { toLoadResult(it, position) }
            .onErrorReturn { LoadResult.Error(it) }
    }

    private fun toLoadResult(data: Followings, position: Int): LoadResult<Int, Followings.Following> {
        return LoadResult.Page(
            data = data.followings,
            prevKey = if(position == 0) null else position - 1,
            nextKey = if(position == data.total - 1) null else position + 1,
        )
    }
}