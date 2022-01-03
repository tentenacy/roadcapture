package com.untilled.roadcapture.data.repository.album

import com.untilled.roadcapture.data.api.RoadCaptureService
import com.untilled.roadcapture.data.dto.album.AlbumResponse
import com.untilled.roadcapture.data.dto.album.AlbumsResponse
import com.untilled.roadcapture.data.dto.comment.CommentsResponse
import retrofit2.Response
import retrofit2.http.Query
import javax.inject.Inject

class AlbumRepositoryImpl
@Inject constructor(
    private val service: RoadCaptureService
) : AlbumRepository {
    // todo: query 추가해야 함
    override suspend fun getAlbumsList(
        @Query(value = "page") page: Int?,
        @Query(value = "size") size: Int?,
        @Query(value = "dateTimeFrom") dateTimeFrom: String,
        @Query(value = "dateTimeTo") dateTimeTo: String
    ): Response<AlbumsResponse> =
        service.getAlbumsList(page?.toString(), size?.toString(), dateTimeFrom, dateTimeTo)

    override suspend fun getCommentsList(albumsId: String): Response<CommentsResponse> =
        service.getCommentsList(albumsId)

    override suspend fun getAlbumDetail(id: String): Response<AlbumResponse> =
        service.getAlbumDetail(id)

}