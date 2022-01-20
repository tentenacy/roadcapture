package com.untilled.roadcapture.data.datasource.paging.album

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.untilled.roadcapture.data.datasource.api.RoadCaptureApi
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsCondition
import com.untilled.roadcapture.data.datasource.api.dto.album.UserAlbumsCondition
import com.untilled.roadcapture.data.entity.paging.Albums
import com.untilled.roadcapture.data.entity.mapper.AlbumsMapper
import com.untilled.roadcapture.data.entity.paging.UserAlbums
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserAlbumsPagingSource @Inject constructor(
    private val mapper: AlbumsMapper,
    private val roadCaptureApi: RoadCaptureApi,
): RxPagingSource<Int, UserAlbums.UserAlbum>() {

    lateinit var userAlbumsCondition: UserAlbumsCondition

    override fun getRefreshKey(state: PagingState<Int, UserAlbums.UserAlbum>): Int? {
        return null
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, UserAlbums.UserAlbum>> {
        val position = params.key ?: 0

        return roadCaptureApi.getUserAlbums(
            page = position,
            size = params.loadSize,
            region1DepthName = userAlbumsCondition.region1DepthName,
            region2DepthName = userAlbumsCondition.region2DepthName,
            region3DepthName = userAlbumsCondition.region3DepthName,
        )
            .subscribeOn(Schedulers.io())
            .map { mapper.transform(it) }
            .map { toLoadResult(it, position) }
            .onErrorReturn { LoadResult.Error(it) }
    }

    private fun toLoadResult(data: UserAlbums, position: Int): LoadResult<Int, UserAlbums.UserAlbum> {
        return LoadResult.Page(
            data = data.userAlbums,
            prevKey = if(position == 0) null else position - 1,
            nextKey = if(position == data.total - 1) null else position + 1,
        )
    }
}