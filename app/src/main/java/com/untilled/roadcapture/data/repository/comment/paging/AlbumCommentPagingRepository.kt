package com.untilled.roadcapture.data.repository.comment.paging

import androidx.paging.PagingData
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsCondition
import com.untilled.roadcapture.data.entity.paging.AlbumComments
import com.untilled.roadcapture.data.entity.paging.Albums
import io.reactivex.rxjava3.core.Flowable

interface AlbumCommentPagingRepository {
    fun getAlbumComments(
        albumId: Long,
    ): Flowable<PagingData<AlbumComments.AlbumComment>>
}