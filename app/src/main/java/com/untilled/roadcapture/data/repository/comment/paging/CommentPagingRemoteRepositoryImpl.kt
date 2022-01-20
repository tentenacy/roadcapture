package com.untilled.roadcapture.data.repository.comment.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import com.untilled.roadcapture.data.datasource.dao.paging.comment.AlbumCommentsDao
import com.untilled.roadcapture.data.datasource.dao.paging.comment.PictureCommentsDao
import com.untilled.roadcapture.data.datasource.paging.comment.AlbumCommentsRemoteMediator
import com.untilled.roadcapture.data.datasource.paging.comment.PictureCommentsRemoteMediator
import com.untilled.roadcapture.data.entity.paging.AlbumComments
import com.untilled.roadcapture.data.entity.paging.PictureComments
import io.reactivex.rxjava3.core.Flowable

@ExperimentalPagingApi
class CommentPagingRemoteRepositoryImpl(
    private val albumCommentsDao: AlbumCommentsDao,
    private val pictureCommentsDao: PictureCommentsDao,
    private val albumCommentsRemoteMediator: AlbumCommentsRemoteMediator,
    private val pictureCommentsRemoteMediator: PictureCommentsRemoteMediator,
): CommentPagingRepository {

    override fun getAlbumComments(albumId: Long): Flowable<PagingData<AlbumComments.AlbumComment>> {
        albumCommentsRemoteMediator.albumId = albumId
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                maxSize = 30,
                prefetchDistance = 5,
                initialLoadSize = 40
            ),
            remoteMediator = albumCommentsRemoteMediator,
            pagingSourceFactory = { albumCommentsDao.selectAll() }
        ).flowable
    }

    override fun getPictureComments(pictureId: Long): Flowable<PagingData<PictureComments.PictureComment>> {
        pictureCommentsRemoteMediator.pictureId = pictureId
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                maxSize = 30,
                prefetchDistance = 5,
                initialLoadSize = 40
            ),
            remoteMediator = pictureCommentsRemoteMediator,
            pagingSourceFactory = { pictureCommentsDao.selectAll() }
        ).flowable
    }
}