package com.untilled.roadcapture.data.datasource.paging.follower

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.untilled.roadcapture.data.datasource.api.RoadCaptureApi
import com.untilled.roadcapture.data.entity.mapper.FollowersMapper
import com.untilled.roadcapture.data.entity.paging.FollowingsSortByAlbum
import com.untilled.roadcapture.utils.applyRetryPolicy
import com.untilled.roadcapture.utils.constant.policy.RetryPolicyConstant
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FollowingsSortByAlbumPagingSource @Inject constructor(
    private val mapper: FollowersMapper,
    private val roadCaptureApi: RoadCaptureApi,
) : RxPagingSource<Int, FollowingsSortByAlbum.FollowingSortByAlbum>() {

    override fun getRefreshKey(state: PagingState<Int, FollowingsSortByAlbum.FollowingSortByAlbum>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, FollowingsSortByAlbum.FollowingSortByAlbum>> {
        val position = params.key ?: 0

        return roadCaptureApi.getFollowingsSortByAlbum(
            page = position,
            size = params.loadSize,
        )
            .delay(1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .map { mapper.transformToFollowingsSortByAlbum(it) }
            .map { toLoadResult(it, position) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                    RetryPolicyConstant.ACCESS_TOKEN_EXPIRED
                ) { LoadResult.Error(it) })
    }

    private fun toLoadResult(
        data: FollowingsSortByAlbum,
        position: Int
    ): LoadResult<Int, FollowingsSortByAlbum.FollowingSortByAlbum> {
        return LoadResult.Page(
            data = data.followingsSortByAlbum,
            prevKey = if (position == 0) null else position - 1,
            nextKey = if (data.endOfPage) null else position + 1,
        )
    }
}