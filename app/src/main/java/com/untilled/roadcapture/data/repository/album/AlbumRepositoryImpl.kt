package com.untilled.roadcapture.data.repository.album

import com.untilled.roadcapture.data.datasource.api.RoadCaptureApi
import com.untilled.roadcapture.data.datasource.api.dto.address.PlaceCondition
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumResponse
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsResponse
import com.untilled.roadcapture.data.datasource.api.dto.album.UserAlbumsResponse
import com.untilled.roadcapture.data.datasource.api.dto.comment.CommentsResponse
import com.untilled.roadcapture.data.datasource.api.dto.common.PageRequest
import com.untilled.roadcapture.data.datasource.api.dto.common.PageResponse
import com.untilled.roadcapture.utils.retryThreeTimes
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
    ): Single<PageResponse<CommentsResponse>> = roadCaptureApi.getAlbumComments(albumId, page, size).retryThreeTimes()

    override fun getPictureCommentsList(
        pictureId: Long,
        page: Int?,
        size: Int?
    ): Single<PageResponse<CommentsResponse>> =
        roadCaptureApi.getPictureComments(pictureId,page,size).retryThreeTimes()

    override fun likesAlbum(albumId: Long): Single<Unit> = roadCaptureApi.likeAlbum(albumId).retryThreeTimes()

    override fun unlikesAlbum(albumId: Long): Single<Unit> = roadCaptureApi.unlikeAlbum(albumId).retryThreeTimes()

    override fun getAlbumDetail(id: Long): Single<AlbumResponse> =
        roadCaptureApi.getAlbumDetail(id).retryThreeTimes()
}