package com.untilled.roadcapture.data.repository.album

import android.util.Log
import com.untilled.roadcapture.data.datasource.api.RoadCaptureApi
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumResponse
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsResponse
import com.untilled.roadcapture.data.datasource.api.dto.comment.CommentsResponse
import com.untilled.roadcapture.data.datasource.api.dto.common.PageResponse
import com.untilled.roadcapture.data.datasource.paging.album.AlbumsPagingSource
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlbumRepositoryImpl
@Inject constructor(
    private val api: RoadCaptureApi,
) : AlbumRepository {
    override suspend fun getAlbumsTemp(
        page: Int?,
        size: Int?,
        dateTimeFrom: String?,
        dateTimeTo: String?
    ): Response<PageResponse<AlbumsResponse>> =
        api.getAlbumsTemp(page, dateTimeFrom, dateTimeTo)

    override fun getAlbumCommentsList(
        albumId: Long,
        page: Int?,
        size: Int?
    ): Single<PageResponse<CommentsResponse>> = api.getAlbumComments(albumId, page, size)

    override fun getPictureCommentsList(
        pictureId: Long,
        page: Int?,
        size: Int?
    ): Single<PageResponse<CommentsResponse>> =
        api.getPictureComments(pictureId,page,size)

    override fun getAlbumDetail(id: Long): Single<AlbumResponse> =
        api.getAlbumDetail(id)
}