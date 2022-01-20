package com.untilled.roadcapture.data.datasource.paging.comment

import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.untilled.roadcapture.data.datasource.api.RoadCaptureApi
import com.untilled.roadcapture.data.entity.mapper.AlbumsMapper
import com.untilled.roadcapture.data.entity.mapper.CommentsMapper
import com.untilled.roadcapture.data.entity.paging.AlbumComments
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.properties.Delegates

@Singleton
class AlbumCommentsPagingSource @Inject constructor(
    private val mapper: CommentsMapper,
    private val roadCaptureApi: RoadCaptureApi,
) : RxPagingSource<Int, AlbumComments.AlbumComment>() {

    var albumId by Delegates.notNull<Long>()

    override fun getRefreshKey(state: PagingState<Int, AlbumComments.AlbumComment>): Int? {
        return null
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, AlbumComments.AlbumComment>> {
        val position = params.key ?: 0

        return roadCaptureApi.getAlbumComments(
            page = position,
            size = params.loadSize,
            albumId = albumId,
        )
            .subscribeOn(Schedulers.io())
            .map { mapper.transform(it) }
            .map { toLoadResult(it, position) }
            .onErrorReturn { PagingSource.LoadResult.Error(it) }
    }

    private fun toLoadResult(
        data: AlbumComments,
        position: Int
    ): LoadResult<Int, AlbumComments.AlbumComment> {
        return LoadResult.Page(
            data = data.albumComments,
            prevKey = if (position == 0) null else position - 1,
            nextKey = if (position == data.total - 1) null else position + 1,
        )
    }
}