package com.untilled.roadcapture.data.datasource.api

import com.untilled.roadcapture.data.datasource.api.dto.comment.CommentsResponse
import com.untilled.roadcapture.data.datasource.api.dto.common.PageResponse
import com.untilled.roadcapture.utils.constant.url.RoadCapturePathConstant
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface CommentApi {
    @GET(RoadCapturePathConstant.GET_ALBUM_COMMENTS)
    fun getAlbumComments(
        @Path("albumId") albumId: Long,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
    ): Single<PageResponse<CommentsResponse>>

    @GET(RoadCapturePathConstant.GET_PICTURE_COMMENTS)
    fun getPictureComments(
        @Path("pictureId") pictureId: Long,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null
    ) : Single<PageResponse<CommentsResponse>>
}