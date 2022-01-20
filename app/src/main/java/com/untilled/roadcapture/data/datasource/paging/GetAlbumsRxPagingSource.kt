package com.untilled.roadcapture.data.datasource.paging

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.untilled.roadcapture.data.datasource.api.RoadCaptureApi
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsCondition
import com.untilled.roadcapture.data.entity.AlbumsPage
import com.untilled.roadcapture.data.entity.mapper.AlbumsMapper
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAlbumsRxPagingSource @Inject constructor(
    private val mapper: AlbumsMapper,
    private val roadCaptureApi: RoadCaptureApi,
): RxPagingSource<Int, AlbumsPage.Albums>() {

    lateinit var albumsCondition: AlbumsCondition

    override fun getRefreshKey(state: PagingState<Int, AlbumsPage.Albums>): Int? {
        return null
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, AlbumsPage.Albums>> {
        val position = params.key ?: 0

        return roadCaptureApi.getAlbums(
            page = position,
            dateTimeFrom = albumsCondition.dateTimeFrom,
            dateTimeTo = albumsCondition.dateTimeTo,
        )
            .subscribeOn(Schedulers.io())
            .map { mapper.transform(it) }
            .map { toLoadResult(it, position) }
            .onErrorReturn { LoadResult.Error(it) }
    }

    private fun toLoadResult(data: AlbumsPage, position: Int): LoadResult<Int, AlbumsPage.Albums> {
        return LoadResult.Page(
            data = data.albums,
            prevKey = if(position == 0) null else position - 1,
            nextKey = if(position == data.total - 1) null else position + 1,
        )
    }
}