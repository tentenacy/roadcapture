package com.untilled.roadcapture.data.datasource.paging.comment

import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.untilled.roadcapture.data.datasource.api.RoadCaptureApi
import com.untilled.roadcapture.data.datasource.api.dto.comment.Comments
import com.untilled.roadcapture.data.entity.mapper.AlbumsMapper
import com.untilled.roadcapture.data.entity.paging.AlbumComments
import com.untilled.roadcapture.data.entity.paging.Albums
import com.untilled.roadcapture.data.repository.album.AlbumRepository
import com.untilled.roadcapture.data.repository.album.paging.AlbumsPagingSource
import io.reactivex.rxjava3.core.Single
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PictureCommentsPagingSource @Inject constructor(
    private val mapper: AlbumsMapper,
    private val roadCaptureApi: RoadCaptureApi,
): RxPagingSource<Int, AlbumComments.AlbumComment>() {
    override fun getRefreshKey(state: PagingState<Int, AlbumComments.AlbumComment>): Int? {
        return null
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, AlbumComments.AlbumComment>> {
        TODO("Not yet implemented")
    }

    private fun toLoadResult(data: Albums, position: Int): LoadResult<Int, Albums.Album> {
        return LoadResult.Page(
            data = data.albums,
            prevKey = if(position == 0) null else position - 1,
            nextKey = if(position == data.total - 1) null else position + 1,
        )
    }
}