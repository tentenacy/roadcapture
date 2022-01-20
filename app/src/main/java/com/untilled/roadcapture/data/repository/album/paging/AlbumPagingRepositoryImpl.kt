package com.untilled.roadcapture.data.repository.album.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsCondition
import com.untilled.roadcapture.data.datasource.paging.album.AlbumsPagingSource
import com.untilled.roadcapture.data.entity.paging.Albums
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject
import javax.inject.Singleton

class AlbumPagingRepositoryImpl(
    private val pagingSource: AlbumsPagingSource,
): AlbumPagingRepository {

    override fun getAlbums(
        cond: AlbumsCondition
    ): Flowable<PagingData<Albums.Album>> {
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