package com.untilled.roadcapture.data.datasource.paging.comment

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.untilled.roadcapture.data.datasource.api.RoadCaptureApi
import com.untilled.roadcapture.data.entity.mapper.CommentsMapper
import com.untilled.roadcapture.data.entity.paging.AlbumComments
import com.untilled.roadcapture.utils.applyRetryPolicy
import com.untilled.roadcapture.utils.constant.policy.RetryPolicyConstant
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.properties.Delegates

@Singleton
class AlbumCommentsPagingSource @Inject constructor(
    private val mapper: CommentsMapper,
    private val roadCaptureApi: RoadCaptureApi,
    private val albumId: Long,
) : RxPagingSource<Int, AlbumComments.AlbumComment>() {

    override fun getRefreshKey(state: PagingState<Int, AlbumComments.AlbumComment>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, AlbumComments.AlbumComment>> {
        val position = params.key ?: 0

        return roadCaptureApi.getAlbumComments(
            page = position,
            size = params.loadSize,
            albumId = albumId,
        )
            .delay(1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .map { mapper.transformToAlbumComments(it) }
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
        data: AlbumComments,
        position: Int
    ): LoadResult<Int, AlbumComments.AlbumComment> {
        return LoadResult.Page(
            data = data.albumComments,
            prevKey = if (position == 0) null else position - 1,
            nextKey = if(data.endOfPage) null else position + 1,
        )
    }
}