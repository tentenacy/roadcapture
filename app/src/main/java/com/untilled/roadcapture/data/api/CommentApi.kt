package com.untilled.roadcapture.data.api

import com.untilled.roadcapture.data.api.dto.comment.CommentsResponse
import com.untilled.roadcapture.data.url.RoadCaptureUrl
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface CommentApi {
    @GET(RoadCaptureUrl.GET_ALBUM_COMMENTS)
    suspend fun getAlbumComments(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("albumId") albumId: Int,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
    ): Response<CommentsResponse>

    @GET(RoadCaptureUrl.GET_PICTURE_COMMENTS)
    suspend fun getPictureComments(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("pictureId") pictureId: Int,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null
    ) : Response<CommentsResponse>
}