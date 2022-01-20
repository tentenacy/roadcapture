package com.untilled.roadcapture.data.repository.album.paging

import androidx.paging.PagingData
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsCondition
import com.untilled.roadcapture.data.datasource.api.dto.album.UserAlbumsCondition
import com.untilled.roadcapture.data.entity.paging.Albums
import com.untilled.roadcapture.data.entity.paging.UserAlbums
import io.reactivex.rxjava3.core.Flowable

interface AlbumPagingRepository {
    fun getAlbums(
        cond: AlbumsCondition,
    ): Flowable<PagingData<Albums.Album>>

    fun getUserAlbums(
        cond: UserAlbumsCondition,
    ): Flowable<PagingData<UserAlbums.UserAlbum>>
}