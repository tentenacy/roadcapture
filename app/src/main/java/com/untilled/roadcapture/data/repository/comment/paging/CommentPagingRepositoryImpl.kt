package com.untilled.roadcapture.data.repository.comment.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import com.untilled.roadcapture.data.datasource.api.RoadCaptureApi
import com.untilled.roadcapture.data.datasource.paging.comment.AlbumCommentsPagingSource
import com.untilled.roadcapture.data.datasource.paging.comment.PictureCommentsPagingSource
import com.untilled.roadcapture.data.entity.mapper.CommentsMapper
import com.untilled.roadcapture.data.entity.paging.AlbumComments
import com.untilled.roadcapture.data.entity.paging.PictureComments
import io.reactivex.rxjava3.core.Flowable

class CommentPagingRepositoryImpl(
    private val mapper: CommentsMapper,
    private val roadCaptureApi: RoadCaptureApi,
): CommentPagingRepository {

    override fun getAlbumComments(albumId: Long): Flowable<PagingData<AlbumComments.AlbumComment>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                prefetchDistance = 5,
                initialLoadSize = 20
            ),
            pagingSourceFactory = { AlbumCommentsPagingSource(mapper, roadCaptureApi, albumId) }
        ).flowable
    }

    override fun getPictureComments(pictureId: Long): Flowable<PagingData<PictureComments.PictureComment>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                prefetchDistance = 5,
                initialLoadSize = 20
            ),
            pagingSourceFactory = { PictureCommentsPagingSource(mapper, roadCaptureApi, pictureId) }
        ).flowable
    }
}