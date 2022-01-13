package com.untilled.roadcapture.data.api

import com.untilled.roadcapture.data.dto.comment.CommentsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface CommentApi {
    @GET("albums/{albumId}/pictures/comments")
    suspend fun getAlbumCommentsList(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("albumId") albumId: Int,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
    ): Response<CommentsResponse>

    @GET("pictures/{pictureId}/comments")
    suspend fun getPictureCommentsList(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("pictureId") pictureId: Int,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null
    ) : Response<CommentsResponse>
}