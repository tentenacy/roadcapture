package com.untilled.roadcapture.data.repository.album

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsCondition
import com.untilled.roadcapture.data.datasource.paging.GetAlbumsRxPagingSource
import com.untilled.roadcapture.data.entity.AlbumsPage
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlbumPagingRepositoryImpl @Inject constructor(
    private val pagingSource: GetAlbumsRxPagingSource,
): AlbumPagingRepository {

    override fun getAlbums(
        cond: AlbumsCondition
    ): Flowable<PagingData<AlbumsPage.Albums>> {
        pagingSource.albumsCondition = cond
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                maxSize = 30,
                prefetchDistance = 5,
                initialLoadSize = 40
            ),
            pagingSourceFactory = { pagingSource }
        ).flowable
    }
}