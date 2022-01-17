package com.untilled.roadcapture.data.datasource.api

import com.untilled.roadcapture.data.datasource.api.dto.comment.CommentsResponse
import com.untilled.roadcapture.utils.constant.url.RoadCapturePathConstant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface CommentApi {
    @GET(RoadCapturePathConstant.GET_ALBUM_COMMENTS)
    suspend fun getAlbumComments(
        @Path("albumId") albumId: Int,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
    ): Response<CommentsResponse>

    @GET(RoadCapturePathConstant.GET_PICTURE_COMMENTS)
    suspend fun getPictureComments(
        @Path("pictureId") pictureId: Int,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null
    ) : Response<CommentsResponse>
}