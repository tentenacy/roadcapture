package com.untilled.roadcapture.data.datasource.paging.album

import android.util.Log
import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.untilled.roadcapture.data.datasource.api.RoadCaptureApi
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsCondition
import com.untilled.roadcapture.data.entity.paging.Albums
import com.untilled.roadcapture.data.entity.mapper.AlbumsMapper
import com.untilled.roadcapture.utils.applyRetryPolicy
import com.untilled.roadcapture.utils.constant.policy.RetryPolicyConstant
import com.untilled.roadcapture.utils.retryThreeTimes
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

class AlbumsPagingSource (
    private val mapper: AlbumsMapper,
    private val roadCaptureApi: RoadCaptureApi,
    private val albumsCondition: AlbumsCondition,
): RxPagingSource<Int, Albums.Album>() {

    override fun getRefreshKey(state: PagingState<Int, Albums.Album>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Albums.Album>> {
        val position = params.key ?: 0
        return roadCaptureApi.getAlbums(
            page = position,
            size = params.loadSize,
            dateTimeFrom = albumsCondition.dateTimeFrom,
            dateTimeTo = albumsCondition.dateTimeTo,
            title = albumsCondition.title
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

    private fun toLoadResult(data: Albums, position: Int): LoadResult<Int, Albums.Album> {
        return LoadResult.Page(
            data = data.albums,
            prevKey = if(position == 0) null else position - 1,
            nextKey = if(data.endOfPage) null else position + 1,
        )
    }
}