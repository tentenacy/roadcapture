package com.untilled.roadcapture.data.repository.album

import com.untilled.roadcapture.data.api.RoadCaptureApi
import com.untilled.roadcapture.data.api.dto.album.AlbumResponse
import com.untilled.roadcapture.data.api.dto.album.AlbumsResponse
import com.untilled.roadcapture.data.api.dto.comment.CommentsResponse
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Query
import javax.inject.Inject

class AlbumRepositoryImpl
@Inject constructor(
    private val api: RoadCaptureApi
) : AlbumRepository {
    // todo: query 추가해야 함
    override suspend fun getAlbumsList(
        @Header("X-AUTH-TOKEN") token: String,
        @Query(value = "page") page: Int?,
        @Query(value = "size") size: Int?,
        @Query(value = "dateTimeFrom") dateTimeFrom: String?,
        @Query(value = "dateTimeTo") dateTimeTo: String?
    ): Response<AlbumsResponse> =
        api.getAlbumsList(token,page?.toString(), size?.toString(), dateTimeFrom, dateTimeTo)

    override suspend fun getAlbumCommentsList(@Header("X-AUTH-TOKEN") token: String,albumsId: Int, page: Int?, size: Int?): Response<CommentsResponse> =
        api.getAlbumCommentsList(token,albumsId, page, size)

    override suspend fun getAlbumDetail(@Header("X-AUTH-TOKEN") token: String,id: String): Response<AlbumResponse> =
        api.getAlbumDetail(token,id)

    override suspend fun getPictureCommentsList(
        @Header("X-AUTH-TOKEN") token: String,
        pictureId: Int,
        page: Int?,
        size: Int?
    ): Response<CommentsResponse> =
        api.getPictureCommentsList(token,pictureId,page,size)
}