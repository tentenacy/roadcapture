package com.untilled.roadcapture.data.repository.album

import com.untilled.roadcapture.data.datasource.api.RoadCaptureApi
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumResponse
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsResponse
import com.untilled.roadcapture.data.datasource.api.dto.comment.CommentsResponse
import com.untilled.roadcapture.data.datasource.api.dto.common.PageResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlbumRepositoryImpl
@Inject constructor(
    private val roadCaptureApi: RoadCaptureApi,
) : AlbumRepository {
    override suspend fun getAlbumsTemp(
        page: Int?,
        size: Int?,
        dateTimeFrom: String?,
        dateTimeTo: String?
    ): Response<PageResponse<AlbumsResponse>> =
        roadCaptureApi.getAlbumsTemp(page, dateTimeFrom, dateTimeTo)

    override fun getAlbumCommentsList(
        albumId: Long,
        page: Int?,
        size: Int?
    ): Single<PageResponse<CommentsResponse>> = roadCaptureApi.getAlbumComments(albumId, page, size)

    override fun getPictureCommentsList(
        pictureId: Long,
        page: Int?,
        size: Int?
    ): Single<PageResponse<CommentsResponse>> =
        roadCaptureApi.getPictureComments(pictureId,page,size)

    override fun likesAlbum(albumId: Long): Single<Unit> = roadCaptureApi.likesAlbum(albumId)

    override fun unlikesAlbum(albumId: Long): Single<Unit> = roadCaptureApi.unlikesAlbum(albumId)

    override fun getAlbumDetail(id: Long): Single<AlbumResponse> =
        roadCaptureApi.getAlbumDetail(id)
}