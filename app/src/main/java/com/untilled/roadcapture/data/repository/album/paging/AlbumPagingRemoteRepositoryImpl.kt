package com.untilled.roadcapture.data.repository.album.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsCondition
import com.untilled.roadcapture.data.datasource.api.dto.album.FollowingAlbumsCondition
import com.untilled.roadcapture.data.datasource.api.dto.album.UserAlbumsCondition
import com.untilled.roadcapture.data.datasource.dao.paging.album.AlbumsDao
import com.untilled.roadcapture.data.datasource.dao.paging.album.UserAlbumsDao
import com.untilled.roadcapture.data.datasource.paging.album.AlbumsRemoteMediator
import com.untilled.roadcapture.data.datasource.paging.album.UserAlbumsRemoteMediator
import com.untilled.roadcapture.data.entity.paging.Albums
import com.untilled.roadcapture.data.entity.paging.UserAlbums
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalPagingApi
class AlbumPagingRemoteRepositoryImpl(
    private val albumsDao: AlbumsDao,
    private val userAlbumsDao: UserAlbumsDao,
    private val albumsRemoteMediator: AlbumsRemoteMediator,
    private val userAlbumsRemoteMediator: UserAlbumsRemoteMediator,
): AlbumPagingRepository {

    override fun albums(
        cond: AlbumsCondition
    ): Flowable<PagingData<Albums.Album>> {
        albumsRemoteMediator.albumsCondition = cond
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                prefetchDistance = 5,
                initialLoadSize = 20
            ),
            remoteMediator = albumsRemoteMediator,
            pagingSourceFactory = { albumsDao.selectAll() }
        ).flowable
    }

    override fun getMyStudioAlbums(cond: UserAlbumsCondition?): Flowable<PagingData<UserAlbums.UserAlbum>> {
        userAlbumsRemoteMediator.userId = null
        userAlbumsRemoteMediator.userAlbumsCondition = cond
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                prefetchDistance = 5,
                initialLoadSize = 20
            ),
            remoteMediator = userAlbumsRemoteMediator,
            pagingSourceFactory = { userAlbumsDao.selectAll() }
        ).flowable
    }

    override fun getStudioAlbums(userId: Long?,cond: UserAlbumsCondition?): Flowable<PagingData<UserAlbums.UserAlbum>> {
        userAlbumsRemoteMediator.userId = userId
        userAlbumsRemoteMediator.userAlbumsCondition = cond
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                prefetchDistance = 5,
                initialLoadSize = 40
            ),
            remoteMediator = userAlbumsRemoteMediator,
            pagingSourceFactory = { userAlbumsDao.selectAll() }
        ).flowable
    }

    override fun getFollowingAlbums(cond: FollowingAlbumsCondition?): Flowable<PagingData<Albums.Album>> {
        TODO("Not yet implemented")
    }
}