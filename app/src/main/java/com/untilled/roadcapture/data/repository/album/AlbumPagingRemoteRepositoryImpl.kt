package com.untilled.roadcapture.data.repository.album

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsCondition
import com.untilled.roadcapture.data.datasource.dao.AlbumsRxDao
import com.untilled.roadcapture.data.datasource.paging.GetAlbumsRxRemoteMediator
import com.untilled.roadcapture.data.entity.AlbumsPage
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlbumPagingRemoteRepositoryImpl @Inject constructor(
    private val albumsRxDao: AlbumsRxDao,
    private val remoteMediator: GetAlbumsRxRemoteMediator
): AlbumPagingRepository {

    @ExperimentalPagingApi
    override fun getAlbums(
        cond: AlbumsCondition
    ): Flowable<PagingData<AlbumsPage.Albums>> {
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
            pagingSourceFactory = { albumsRxDao.selectAll() }
        ).flowable
    }
}