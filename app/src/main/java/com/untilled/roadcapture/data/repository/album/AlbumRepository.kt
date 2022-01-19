package com.untilled.roadcapture.data.repository.album

import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumResponse
import com.untilled.roadcapture.data.datasource.api.dto.comment.CommentsResponse
import com.untilled.roadcapture.data.datasource.api.dto.common.PageResponse
import retrofit2.Response
import retrofit2.http.Path
import retrofit2.http.Query

interface AlbumRepository {
    // todo: query 추가해야 함
    suspend fun getAlbumsList(
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("dateTimeFrom")dateTimeFrom: String?,
        @Query("dateTimeTo")dateTimeTo: String?
    ): Response<PageResponse<AlbumResponse>>

    suspend fun getAlbumCommentsList(
        @Path("albumId") albumId: Int,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null
    ): Response<CommentsResponse>

    suspend fun getAlbumDetail(
        @Path("id") id: String
    ): Response<AlbumResponse>

    suspend fun getPictureCommentsList(
        @Path("pictureId") pictureId: Int,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null
    ): Response<CommentsResponse>

}