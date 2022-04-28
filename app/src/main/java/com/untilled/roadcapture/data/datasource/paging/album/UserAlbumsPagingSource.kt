package com.untilled.roadcapture.data.datasource.paging.album

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.untilled.roadcapture.data.datasource.api.RoadCaptureApi
import com.untilled.roadcapture.data.datasource.api.dto.album.UserAlbumsCondition
import com.untilled.roadcapture.data.entity.mapper.AlbumsMapper
import com.untilled.roadcapture.data.entity.paging.UserAlbums
import com.untilled.roadcapture.utils.applyRetryPolicy
import com.untilled.roadcapture.utils.constant.policy.RetryPolicyConstant
import com.untilled.roadcapture.utils.retryThreeTimes
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

class UserAlbumsPagingSource(
    private val mapper: AlbumsMapper,
    private val roadCaptureApi: RoadCaptureApi,
    private val userAlbumsCondition: UserAlbumsCondition?,
    private val userId: Long? = null,
): RxPagingSource<Int, UserAlbums.UserAlbum>() {

    override fun getRefreshKey(state: PagingState<Int, UserAlbums.UserAlbum>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, UserAlbums.UserAlbum>> {
        val position = params.key ?: 0

        if(userId == null) {
            return roadCaptureApi.getMyStudioAlbums(
                page = position,
                size = params.loadSize,
                region1DepthName = userAlbumsCondition?.region1DepthName,
                region2DepthName = userAlbumsCondition?.region2DepthName,
                region3DepthName = userAlbumsCondition?.region3DepthName,
            )
                .subscribeOn(Schedulers.io())
                .map { mapper.transform(it) }
                .map { toLoadResult(it, position) }
                .onErrorReturn { LoadResult.Error(it) }
                .retryThreeTimes()
        } else{
            return roadCaptureApi.getStudioAlbums(
                userId = userId,
                page = position,
                size = params.loadSize,
                region1DepthName = userAlbumsCondition?.region1DepthName,
                region2DepthName = userAlbumsCondition?.region2DepthName,
                region3DepthName = userAlbumsCondition?.region3DepthName,
            )
                .subscribeOn(Schedulers.io())
                .map { mapper.transform(it) }
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

    private fun toLoadResult(data: UserAlbums, position: Int): LoadResult<Int, UserAlbums.UserAlbum> {
        return LoadResult.Page(
            data = data.userAlbums,
            prevKey = if(position == 0) null else position - 1,
            nextKey = if(data.endOfPage) null else position + 1,
        )
    }
}