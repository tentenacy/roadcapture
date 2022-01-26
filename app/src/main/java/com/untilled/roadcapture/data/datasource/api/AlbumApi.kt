package com.untilled.roadcapture.data.datasource.api

import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumCreateRequest
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumResponse
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsResponse
import com.untilled.roadcapture.data.datasource.api.dto.album.UserAlbumsResponse
import com.untilled.roadcapture.data.datasource.api.dto.comment.CommentCreateRequest
import com.untilled.roadcapture.data.datasource.api.dto.common.PageResponse
import com.untilled.roadcapture.utils.constant.url.RoadCapturePathConstant
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface AlbumApi {

    @GET(RoadCapturePathConstant.GET_ALBUMS)
    fun getAlbums(
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("sort") sort: String? = "createdAt,desc",
        @Query("dateTimeFrom") dateTimeFrom: String? = null,
        @Query("dateTimeTo") dateTimeTo: String? = null,
        @Query("title") title: String? = null
    ): Single<PageResponse<AlbumsResponse>>

    @GET(RoadCapturePathConstant.GET_MY_ALBUMS)
    fun getMyStudioAlbums(
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("sort") sort: String? = "createdAt,desc",
        @Query("placeCond.region1DepthName") region1DepthName: String? = null,
        @Query("placeCond.region2DepthName") region2DepthName: String? = null,
        @Query("placeCond.region3DepthName") region3DepthName: String? = null,
    ): Single<PageResponse<UserAlbumsResponse>>

    @GET(RoadCapturePathConstant.GET_USER_ALBUMS)
    fun getStudioAlbums(
        @Path("userId") userId: Long?,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("sort") sort: String? = "createdAt,desc",
        @Query("placeCond.region1DepthName") region1DepthName: String? = null,
        @Query("placeCond.region2DepthName") region2DepthName: String? = null,
        @Query("placeCond.region3DepthName") region3DepthName: String? = null,
    ): Single<PageResponse<UserAlbumsResponse>>

    @GET(RoadCapturePathConstant.GET_ALBUM)
    fun getAlbumDetail(
        @Path("id") id: Long,
    ): Single<AlbumResponse>

    @GET(RoadCapturePathConstant.GET_FOLLOWINGS_ALBUMS)
    fun getFollowingAlbums(
        @Query("followingId") id: Long? = null,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("sort") sort: String? = "createdAt,desc",
    ): Single<PageResponse<AlbumsResponse>>

    @Multipart
    @POST(RoadCapturePathConstant.POST_ALBUM)
    fun postAlbum(
        @Body request: AlbumCreateRequest
    ) : Single<Unit>
}