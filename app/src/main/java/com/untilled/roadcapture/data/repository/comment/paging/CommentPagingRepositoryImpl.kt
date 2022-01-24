package com.untilled.roadcapture.data.repository.comment.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import com.untilled.roadcapture.data.datasource.paging.comment.AlbumCommentsPagingSource
import com.untilled.roadcapture.data.datasource.paging.comment.PictureCommentsPagingSource
import com.untilled.roadcapture.data.entity.paging.AlbumComments
import com.untilled.roadcapture.data.entity.paging.PictureComments
import io.reactivex.rxjava3.core.Flowable

class CommentPagingRepositoryImpl(
    private val albumCommentsPagingSource: AlbumCommentsPagingSource,
    private val pictureCommentsPagingSource: PictureCommentsPagingSource,
): CommentPagingRepository {

    override fun getAlbumComments(albumId: Long): Flowable<PagingData<AlbumComments.AlbumComment>> {
        albumCommentsPagingSource.albumId = albumId
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                maxSize = 30,
                prefetchDistance = 5,
                initialLoadSize = 20
            ),
            pagingSourceFactory = { albumCommentsPagingSource }
        ).flowable
    }

    override fun getPictureComments(pictureId: Long): Flowable<PagingData<PictureComments.PictureComment>> {
        pictureCommentsPagingSource.pictureId = pictureId
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                maxSize = 30,
                prefetchDistance = 5,
                initialLoadSize = 20
            ),
            pagingSourceFactory = { pictureCommentsPagingSource }
        ).flowable
    }
}