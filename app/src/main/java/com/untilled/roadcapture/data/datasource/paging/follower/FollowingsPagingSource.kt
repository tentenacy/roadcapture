package com.untilled.roadcapture.data.datasource.paging.follower

import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.untilled.roadcapture.data.datasource.api.RoadCaptureApi
import com.untilled.roadcapture.data.datasource.api.dto.user.FollowingsCondition
import com.untilled.roadcapture.data.entity.mapper.FollowersMapper
import com.untilled.roadcapture.data.entity.paging.Followings
import com.untilled.roadcapture.utils.applyRetryPolicy
import com.untilled.roadcapture.utils.constant.policy.RetryPolicyConstant
import com.untilled.roadcapture.utils.retryThreeTimes
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

    var userId: Long? = null
    var followingsCondition: FollowingsCondition? = null

    override fun getRefreshKey(state: PagingState<Int, Followings.Following>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Followings.Following>> {
        val position = params.key ?: 0

        if(userId == null) {
            return roadCaptureApi.getFollowings(
                page = position,
                size = params.loadSize,
                username = followingsCondition?.username,
            )
                .subscribeOn(Schedulers.io())
                .map { mapper.transformToFollowings(it) }
                .map { toLoadResult(it, position) }
                .compose(
                    applyRetryPolicy(
                        RetryPolicyConstant.TIMEOUT,
                        RetryPolicyConstant.NETWORK,
                        RetryPolicyConstant.SERVICE_UNAVAILABLE,
                        RetryPolicyConstant.ACCESS_TOKEN_EXPIRED
                    ) { LoadResult.Error(it) })
        }
        else {
            return roadCaptureApi.getUserFollowings(
                page = position,
                size = params.loadSize,
                id = userId,
                username = followingsCondition?.username,
            )
                .subscribeOn(Schedulers.io())
                .map { mapper.transformToFollowings(it) }
                .map { toLoadResult(it, position) }
                .compose(
                    applyRetryPolicy(
                        RetryPolicyConstant.TIMEOUT,
                        RetryPolicyConstant.NETWORK,
                        RetryPolicyConstant.SERVICE_UNAVAILABLE,
                        RetryPolicyConstant.ACCESS_TOKEN_EXPIRED
                    ) { LoadResult.Error(it) })
        }
    }

    private fun toLoadResult(data: Followings, position: Int): LoadResult<Int, Followings.Following> {
        return LoadResult.Page(
            data = data.followings,
            prevKey = if(position == 0) null else position - 1,
            nextKey = if(data.endOfPage) null else position + 1,
        )
    }
}