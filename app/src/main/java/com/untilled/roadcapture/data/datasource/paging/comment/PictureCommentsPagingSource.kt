package com.untilled.roadcapture.data.datasource.paging.comment

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.untilled.roadcapture.data.datasource.api.RoadCaptureApi
import com.untilled.roadcapture.data.entity.mapper.CommentsMapper
import com.untilled.roadcapture.data.entity.paging.PictureComments
import com.untilled.roadcapture.utils.applyRetryPolicy
import com.untilled.roadcapture.utils.constant.policy.RetryPolicyConstant
import com.untilled.roadcapture.utils.retryThreeTimes
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.properties.Delegates

@Singleton
class PictureCommentsPagingSource @Inject constructor(
    private val mapper: CommentsMapper,
    private val roadCaptureApi: RoadCaptureApi,
    private val pictureId: Long,
) : RxPagingSource<Int, PictureComments.PictureComment>() {

    override fun getRefreshKey(state: PagingState<Int, PictureComments.PictureComment>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, PictureComments.PictureComment>> {
        val position = params.key ?: 0

        return roadCaptureApi.getPictureComments(
            page = position,
            size = params.loadSize,
            pictureId = pictureId,
        )
            .delay(1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .map { mapper.transformToPictureComments(it) }
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
        data: PictureComments,
        position: Int
    ): LoadResult<Int, PictureComments.PictureComment> {
        return LoadResult.Page(
            data = data.pictureComments,
            prevKey = if (position == 0) null else position - 1,
            nextKey = if(data.endOfPage) null else position + 1,
        )
    }
}