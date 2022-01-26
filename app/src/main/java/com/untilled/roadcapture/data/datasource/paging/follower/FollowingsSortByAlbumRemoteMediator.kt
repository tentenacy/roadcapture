package com.untilled.roadcapture.data.datasource.paging.follower

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.rxjava3.RxRemoteMediator
import com.untilled.roadcapture.data.datasource.api.RoadCaptureApi
import com.untilled.roadcapture.data.datasource.database.PagingDatabase
import com.untilled.roadcapture.data.entity.mapper.FollowersMapper
import com.untilled.roadcapture.data.entity.paging.FollowingsSortByAlbum
import com.untilled.roadcapture.utils.applyRetryPolicy
import com.untilled.roadcapture.utils.constant.policy.RetryPolicyConstant
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.InvalidObjectException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class FollowingsSortByAlbumRemoteMediator @Inject constructor(
    private val mapper: FollowersMapper,
    private val roadCaptureApi: RoadCaptureApi,
    private val database: PagingDatabase,
): RxRemoteMediator<Int, FollowingsSortByAlbum.FollowingSortByAlbum>() {

    override fun loadSingle(
        loadType: LoadType,
        state: PagingState<Int, FollowingsSortByAlbum.FollowingSortByAlbum>
    ): Single<MediatorResult> {
        return Single.just(loadType)
            .subscribeOn(Schedulers.io())
            .map {
                when(it) {
                    LoadType.REFRESH -> {
                        val remoteKeys = getRemoteKeyClosetsToCurrentPosition(state)

                        remoteKeys?.nextKey?.minus(1) ?: 0
                    }
                    LoadType.PREPEND -> {
                        val remoteKeys = getRemoteKeyForFirstItem(state)
                            ?: throw InvalidObjectException("Result is empty")

                        remoteKeys.prevKey ?: INVALID_PAGE
                    }
                    LoadType.APPEND -> {
                        val remoteKeys = getRemoteKeyForLastItem(state)
                            ?: throw InvalidObjectException("Result is empty")

                        remoteKeys.nextKey
                    }
                }
            }
            .flatMap { page ->
                if(page == INVALID_PAGE) {
                    Single.just(MediatorResult.Success(endOfPaginationReached = true))
                } else {
                    roadCaptureApi.getFollowingsSortByAlbum(
                        page = page,
                        size = state.config.pageSize,
                    )
                        .subscribeOn(Schedulers.io())
                        .map { mapper.transformToFollowingsSortByAlbum(it) }
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
    private fun insertToDb(page: Int, loadType: LoadType, data: FollowingsSortByAlbum): FollowingsSortByAlbum {
        database.beginTransaction()

        try {
            if (loadType == LoadType.REFRESH) {
                database.followingsSortByAlbumDao().clearFollowingsSortByAlbum()
                database.followingsSortByAlbumRemoteKeysDao().clearRemoteKeys()
            }

            val prevKey = if (page == 0) null else page - 1
            val nextKey = if (data.endOfPage) null else page + 1
            val keys = data.followingsSortByAlbum.map {
                FollowingsSortByAlbum.FollowingSortByAlbumRemoteKeys(followingSortByAlbumId = it.followingSortByAlbumId, prevKey = prevKey, nextKey = nextKey ?: INVALID_PAGE)
            }
            database.followingsSortByAlbumRemoteKeysDao().insertAll(keys)
            database.followingsSortByAlbumDao().insertAll(data.followingsSortByAlbum)
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
    private fun getRemoteKeyForLastItem(state: PagingState<Int, FollowingsSortByAlbum.FollowingSortByAlbum>): FollowingsSortByAlbum.FollowingSortByAlbumRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { followingSortByAlbumRemoteKeys ->
            database.followingsSortByAlbumRemoteKeysDao().remoteKeysByFollowingSortByAlbumId(followingSortByAlbumRemoteKeys.followingSortByAlbumId)
        }
    }

    /**
     * will try to get first remote key found in the first movie data.
     * This method will be called during PREPEND event,
     * means that we should provide previous key to load movie data before scroll to top ended
     */
    private fun getRemoteKeyForFirstItem(state: PagingState<Int, FollowingsSortByAlbum.FollowingSortByAlbum>): FollowingsSortByAlbum.FollowingSortByAlbumRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { followingSortByAlbumRemoteKeys ->
            database.followingsSortByAlbumRemoteKeysDao().remoteKeysByFollowingSortByAlbumId(followingSortByAlbumRemoteKeys.followingSortByAlbumId)
        }
    }

    /**
     * will search for page closes to current scroll position,
     * if return null means this is the initial page load
     */
    private fun getRemoteKeyClosetsToCurrentPosition(state: PagingState<Int, FollowingsSortByAlbum.FollowingSortByAlbum>): FollowingsSortByAlbum.FollowingSortByAlbumRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.followingSortByAlbumId?.let { id ->
                database.followingsSortByAlbumRemoteKeysDao().remoteKeysByFollowingSortByAlbumId(id)
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