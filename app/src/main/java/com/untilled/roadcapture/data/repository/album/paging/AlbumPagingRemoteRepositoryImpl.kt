package com.untilled.roadcapture.data.repository.album.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsCondition
import com.untilled.roadcapture.data.datasource.dao.paging.album.AlbumsDao
import com.untilled.roadcapture.data.datasource.paging.album.AlbumsRemoteMediator
import com.untilled.roadcapture.data.entity.paging.Albums
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject
import javax.inject.Singleton

class AlbumPagingRemoteRepositoryImpl(
    private val albumsDao: AlbumsDao,
    private val remoteMediator: AlbumsRemoteMediator
): AlbumPagingRepository {

    @ExperimentalPagingApi
    override fun getAlbums(
        cond: AlbumsCondition
    ): Flowable<PagingData<Albums.Album>> {
        remoteMediator.albumsCondition = cond
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                maxSize = 30,
                prefetchDistance = 5,
                initialLoadSize = 40
            ),
            remoteMediator = remoteMediator,
            pagingSourceFactory = { albumsDao.selectAll() }
        ).flowable
    }
}