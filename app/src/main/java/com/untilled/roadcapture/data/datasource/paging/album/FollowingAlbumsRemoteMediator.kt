package com.untilled.roadcapture.data.datasource.paging.album

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.rxjava3.RxRemoteMediator
import com.untilled.roadcapture.data.datasource.api.RoadCaptureApi
import com.untilled.roadcapture.data.datasource.api.dto.album.FollowingAlbumsCondition
import com.untilled.roadcapture.data.datasource.database.PagingDatabase
import com.untilled.roadcapture.data.entity.mapper.AlbumsMapper
import com.untilled.roadcapture.data.entity.paging.Albums
import com.untilled.roadcapture.utils.applyRetryPolicy
import com.untilled.roadcapture.utils.constant.policy.RetryPolicyConstant
import com.untilled.roadcapture.utils.retryThreeTimes
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.InvalidObjectException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class FollowingAlbumsRemoteMediator @Inject constructor(
    private val mapper: AlbumsMapper,
    private val roadCaptureApi: RoadCaptureApi,
    private val database: PagingDatabase
): RxRemoteMediator<Int,Albums.Album>() {

    var followingAlbumsCondition: FollowingAlbumsCondition? = null
    override fun loadSingle(
        loadType: LoadType,
        state: PagingState<Int, Albums.Album>
    ): Single<MediatorResult> {
        return Single.just(loadType)
            .subscribeOn(Schedulers.io())
            .map {
                when(it){
                    LoadType.REFRESH -> {
                        val remoteKeys = getRemoteKeyClosetsToCurrentPosition(state)

                        remoteKeys?.nextKey?.minus(1) ?: 1
                    }
                    LoadType.PREPEND -> {
                        val remoteKeys = getRemoteKeyForFirstItem(state)
                            ?: throw InvalidObjectException("Result is empty")

                        remoteKeys.prevKey ?: UserAlbumsRemoteMediator.INVALID_PAGE
                    }
                    LoadType.APPEND -> {
                        val remoteKeys = getRemoteKeyForLastItem(state)
                            ?: throw InvalidObjectException("Result is empty")

                        remoteKeys.nextKey
                    }
                }
            }.flatMap { page ->
                if(page == UserAlbumsRemoteMediator.INVALID_PAGE) {
                    Single.just(MediatorResult.Success(endOfPaginationReached = true))
                } else {
                    roadCaptureApi.getFollowingAlbums(
                        id = followingAlbumsCondition?.followingId,
                        page = page,
                        sort = null
                    )
                        .subscribeOn(Schedulers.io())
                        .map { mapper.transform(it) }
                        .map { insertToDb(page, loadType, it) }
                        .map<MediatorResult> { MediatorResult.Success(endOfPaginationReached = it.endOfPage) }
                        .compose(
                            applyRetryPolicy(
                                RetryPolicyConstant.TIMEOUT,
                                RetryPolicyConstant.NETWORK,
                                RetryPolicyConstant.SERVICE_UNAVAILABLE,
                                RetryPolicyConstant.ACCESS_TOKEN_EXPIRED
                            ) { MediatorResult.Error(it) })
                }
            }
    }

    @Suppress("DEPRECATION")
    private fun insertToDb(page: Int, loadType: LoadType, data: Albums): Albums {
        database.beginTransaction()

        try {
            if (loadType == LoadType.REFRESH) {
                database.albumsRemoteKeysDao().clearRemoteKeys()
                database.albumsDao().clearAlbums()
            }

            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (data.endOfPage) null else page + 1
            val keys = data.albums.map {
                Albums.AlbumRemoteKeys(albumsId = it.albumsId, prevKey = prevKey, nextKey = nextKey ?: AlbumsRemoteMediator.INVALID_PAGE)
            }
            database.albumsRemoteKeysDao().insertAll(keys)
            database.albumsDao().insertAll(data.albums)
            database.setTransactionSuccessful()

        } finally {
            database.endTransaction()
        }

        return data
    }

    /**
     * will try to get last remote key found in the last movie data.
     * This method will be called during APPEND event,
     * means that we should provide next key to load movie data before scroll to bottom ended
     */
    private fun getRemoteKeyForLastItem(state: PagingState<Int, Albums.Album>): Albums.AlbumRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { albums ->
            database.albumsRemoteKeysDao().remoteKeysByAlbumsId(albums.id)
        }
    }

    /**
     * will try to get first remote key found in the first movie data.
     * This method will be called during PREPEND event,
     * means that we should provide previous key to load movie data before scroll to top ended
     */
    private fun getRemoteKeyForFirstItem(state: PagingState<Int, Albums.Album>): Albums.AlbumRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { albums ->
            database.albumsRemoteKeysDao().remoteKeysByAlbumsId(albums.id)
        }
    }

    /**
     * will search for page closes to current scroll position,
     * if return null means this is the initial page load
     */
    private fun getRemoteKeyClosetsToCurrentPosition(state: PagingState<Int, Albums.Album>): Albums.AlbumRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.albumsId?.let { id ->
                database.albumsRemoteKeysDao().remoteKeysByAlbumsId(id)
            }
        }
    }

    companion object {
        /**
         * return if user already reached the end of the page and just
         */
        const val INVALID_PAGE = -1
    }
}