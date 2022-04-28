package com.untilled.roadcapture.data.repository.album.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import com.untilled.roadcapture.data.datasource.api.RoadCaptureApi
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsCondition
import com.untilled.roadcapture.data.datasource.api.dto.album.FollowingAlbumsCondition
import com.untilled.roadcapture.data.datasource.api.dto.album.UserAlbumsCondition
import com.untilled.roadcapture.data.datasource.paging.album.AlbumsPagingSource
import com.untilled.roadcapture.data.datasource.paging.album.FollowingAlbumsPagingSource
import com.untilled.roadcapture.data.datasource.paging.album.UserAlbumsPagingSource
import com.untilled.roadcapture.data.entity.mapper.AlbumsMapper
import com.untilled.roadcapture.data.entity.paging.Albums
import com.untilled.roadcapture.data.entity.paging.UserAlbums
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject
import javax.inject.Singleton

class AlbumPagingRepositoryImpl(
    private val albumsPagingSource: AlbumsPagingSource,
    private val followingAlbumsPagingSource: FollowingAlbumsPagingSource,
    private val mapper: AlbumsMapper,
    private val roadCaptureApi: RoadCaptureApi,
): AlbumPagingRepository {

    override fun getAlbums(
        cond: AlbumsCondition
    ): Flowable<PagingData<Albums.Album>> {
        albumsPagingSource.albumsCondition = cond
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                maxSize = 30,
                prefetchDistance = 5,
                initialLoadSize = 20
            ),
            pagingSourceFactory = { albumsPagingSource }
        ).flowable
    }

    override fun getMyStudioAlbums(cond: UserAlbumsCondition?): Flowable<PagingData<UserAlbums.UserAlbum>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
//                pageSize = 20,
                enablePlaceholders = true,
                maxSize = 30,
                prefetchDistance = 1,
//                prefetchDistance = 5,
                initialLoadSize = 5
//                initialLoadSize = 20
            ),
            pagingSourceFactory = { UserAlbumsPagingSource(mapper, roadCaptureApi, userAlbumsCondition = cond) }
        ).flowable
    }

    override fun getStudioAlbums(userId: Long?,cond: UserAlbumsCondition?): Flowable<PagingData<UserAlbums.UserAlbum>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
//                pageSize = 20,
                enablePlaceholders = true,
                maxSize = 30,
                prefetchDistance = 1,
//                prefetchDistance = 5,
                initialLoadSize = 5
//                initialLoadSize = 20
            ),
            pagingSourceFactory = { UserAlbumsPagingSource(mapper, roadCaptureApi, userAlbumsCondition = cond, userId = userId) }
        ).flowable
    }

    override fun getFollowingAlbums(cond: FollowingAlbumsCondition?): Flowable<PagingData<Albums.Album>> {
        followingAlbumsPagingSource.followingAlbumsCondition = cond
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                maxSize = 30,
                prefetchDistance = 5,
                initialLoadSize = 20
            ),
            pagingSourceFactory = { followingAlbumsPagingSource }
        ).flowable
    }
}