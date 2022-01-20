package com.untilled.roadcapture.data.repository.comment.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsCondition
import com.untilled.roadcapture.data.datasource.dao.paging.album.AlbumsDao
import com.untilled.roadcapture.data.datasource.dao.paging.comment.AlbumCommentsDao
import com.untilled.roadcapture.data.datasource.dao.paging.comment.AlbumCommentsRemoteKeysDao
import com.untilled.roadcapture.data.datasource.paging.album.AlbumsRemoteMediator
import com.untilled.roadcapture.data.datasource.paging.comment.AlbumCommentsRemoteMediator
import com.untilled.roadcapture.data.entity.paging.AlbumComments
import com.untilled.roadcapture.data.entity.paging.Albums
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject
import javax.inject.Singleton

class AlbumCommentPagingRemoteRepositoryImpl(
    private val albumCommentsDao: AlbumCommentsDao,
    private val remoteMediator: AlbumCommentsRemoteMediator
): AlbumCommentPagingRepository {

    @ExperimentalPagingApi
    override fun getAlbumComments(albumId: Long): Flowable<PagingData<AlbumComments.AlbumComment>> {
        remoteMediator.albumId = albumId
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                maxSize = 30,
                prefetchDistance = 5,
                initialLoadSize = 40
            ),
            remoteMediator = remoteMediator,
            pagingSourceFactory = { albumCommentsDao.selectAll() }
        ).flowable
    }
}