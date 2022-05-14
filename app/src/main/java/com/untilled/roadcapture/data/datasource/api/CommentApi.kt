package com.untilled.roadcapture.data.datasource.api

import com.untilled.roadcapture.data.datasource.api.dto.comment.CommentCreateRequest
import com.untilled.roadcapture.data.datasource.api.dto.comment.CommentsResponse
import com.untilled.roadcapture.data.datasource.api.dto.common.PageResponse
import com.untilled.roadcapture.utils.constant.url.RoadCapturePathConstant
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.*

interface CommentApi {
    @GET(RoadCapturePathConstant.GET_ALBUM_COMMENTS)
    fun getAlbumComments(
        @Path("albumId") albumId: Long,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("sort") sort: String? = "createdAt,desc",
    ): Single<PageResponse<CommentsResponse>>

    @GET(RoadCapturePathConstant.GET_PICTURE_COMMENTS)
    fun getPictureComments(
        @Path("pictureId") pictureId: Long,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("sort") sort: String? = "createdAt,desc",
    ) : Single<PageResponse<CommentsResponse>>

    @POST(RoadCapturePathConstant.POST_PICTURE_COMMENTS)
    fun postPictureComment(
        @Path("pictureId") pictureId: Long,
        @Body request: CommentCreateRequest
    ): Single<Unit>

    @DELETE(RoadCapturePathConstant.DELETE_COMMENT)
    fun deleteComment(
        @Path("commentId") commentId: Long,
    ): Completable
}