package com.untilled.roadcapture.data.repository.album.paging

import androidx.paging.PagingData
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsCondition
import com.untilled.roadcapture.data.entity.paging.Albums
import io.reactivex.rxjava3.core.Flowable

interface AlbumPagingRepository {
    fun getAlbums(
        cond: AlbumsCondition,
    ): Flowable<PagingData<Albums.Album>>
}