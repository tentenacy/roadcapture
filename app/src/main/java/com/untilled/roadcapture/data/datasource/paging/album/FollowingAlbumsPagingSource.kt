package com.untilled.roadcapture.data.datasource.paging.album

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.untilled.roadcapture.data.datasource.api.RoadCaptureApi
import com.untilled.roadcapture.data.datasource.api.dto.album.FollowingAlbumsCondition
import com.untilled.roadcapture.data.entity.mapper.AlbumsMapper
import com.untilled.roadcapture.data.entity.paging.Albums
import com.untilled.roadcapture.utils.retryThreeTimes
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FollowingAlbumsPagingSource @Inject constructor(
    private val mapper: AlbumsMapper,
    private val roadCaptureApi: RoadCaptureApi
): RxPagingSource<Int, Albums.Album>() {

    var followingAlbumsCondition: FollowingAlbumsCondition? = null

    override fun getRefreshKey(state: PagingState<Int, Albums.Album>): Int? {
        return null
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Albums.Album>> {
        val position = params.key ?: 0

        return roadCaptureApi.getFollowingAlbums(
            id = followingAlbumsCondition?.followingId,
            page = position,
            size = params.loadSize,
            sort = null
        )
            .subscribeOn(Schedulers.io())
            .map { mapper.transform(it) }
            .map { toLoadResult(it,position) }
            .onErrorReturn{ LoadResult.Error(it) }
    }

    private fun toLoadResult(data: Albums, position: Int): LoadResult<Int, Albums.Album> {
        return LoadResult.Page(
            data = data.albums,
            prevKey = if(position == 0) null else position - 1,
            nextKey = if(position == data.total) null else position + 1,
        )
    }
}