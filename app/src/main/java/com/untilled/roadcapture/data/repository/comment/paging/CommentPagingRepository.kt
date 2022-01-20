package com.untilled.roadcapture.data.repository.comment.paging

import androidx.paging.PagingData
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsCondition
import com.untilled.roadcapture.data.entity.paging.AlbumComments
import com.untilled.roadcapture.data.entity.paging.Albums
import com.untilled.roadcapture.data.entity.paging.PictureComments
import io.reactivex.rxjava3.core.Flowable

interface CommentPagingRepository {
    fun getAlbumComments(
        albumId: Long,
    ): Flowable<PagingData<AlbumComments.AlbumComment>>

    fun getPictureComments(
        pictureId: Long,
    ): Flowable<PagingData<PictureComments.PictureComment>>
}