package com.untilled.roadcapture.data.repository.album

import com.untilled.roadcapture.data.datasource.api.RoadCaptureApi
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumResponse
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsResponse
import com.untilled.roadcapture.data.datasource.api.dto.comment.CommentsResponse
import com.untilled.roadcapture.data.datasource.api.dto.common.PageResponse
import retrofit2.Response
import retrofit2.http.Query
import javax.inject.Inject

class AlbumRepositoryImpl
@Inject constructor(
    private val api: RoadCaptureApi
) : AlbumRepository {
    // todo: query 추가해야 함
    override suspend fun getAlbums(
        @Query(value = "page") page: Int?,
        @Query(value = "size") size: Int?,
        @Query(value = "dateTimeFrom") dateTimeFrom: String?,
        @Query(value = "dateTimeTo") dateTimeTo: String?
    ): Response<PageResponse<AlbumsResponse>> =
        api.getAlbums(page?.toString(), size?.toString(), dateTimeFrom, dateTimeTo)

    override suspend fun getAlbumCommentsList(albumsId: Int, page: Int?, size: Int?): Response<CommentsResponse> =
        api.getAlbumComments(albumsId, page, size)

    override suspend fun getAlbumDetail(id: Int): Response<AlbumResponse> =
        api.getAlbumDetail(id)

    override suspend fun getPictureCommentsList(
        pictureId: Int,
        page: Int?,
        size: Int?
    ): Response<CommentsResponse> =
        api.getPictureComments(pictureId,page,size)
}