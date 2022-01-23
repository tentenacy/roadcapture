package com.untilled.roadcapture.data.repository.album.paging

import androidx.paging.PagingData
import com.untilled.roadcapture.data.datasource.api.dto.address.PlaceCondition
import com.untilled.roadcapture.data.datasource.api.dto.album.*
import com.untilled.roadcapture.data.datasource.api.dto.common.PageRequest
import com.untilled.roadcapture.data.datasource.api.dto.common.PageResponse
import com.untilled.roadcapture.data.entity.paging.Albums
import com.untilled.roadcapture.data.entity.paging.UserAlbums
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

interface AlbumPagingRepository {
    fun getAlbums(
        cond: AlbumsCondition,
    ): Flowable<PagingData<Albums.Album>>

    fun getMyStudioAlbums(
        cond: UserAlbumsCondition?
    ): Flowable<PagingData<UserAlbums.UserAlbum>>

    fun getStudioAlbums(
        userId: Long?,
        cond: UserAlbumsCondition?,
    ): Flowable<PagingData<UserAlbums.UserAlbum>>

    fun getFollowingAlbums(cond: FollowingAlbumsCondition?): Flowable<PagingData<Albums.Album>>
}