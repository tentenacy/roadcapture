package com.untilled.roadcapture.data.datasource.api

import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumResponse
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsResponse
import com.untilled.roadcapture.data.datasource.api.dto.album.UserAlbumsResponse
import com.untilled.roadcapture.data.datasource.api.dto.common.PageResponse
import com.untilled.roadcapture.utils.constant.url.RoadCapturePathConstant
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.*

interface AlbumApi {

    @GET(RoadCapturePathConstant.GET_ALBUMS)
    fun getAlbumsTemp(
        @Query("page") page: Int? = null,
        @Query("dateTimeFrom") dateTimeFrom: String? = null,
        @Query("dateTimeTo") dateTimeTo: String? = null,
    ): Response<PageResponse<AlbumsResponse>>

    @GET(RoadCapturePathConstant.GET_ALBUMS)
    fun getAlbums(
        @Query("page") page: Int? = null,
        @Query("dateTimeFrom") dateTimeFrom: String? = null,
        @Query("dateTimeTo") dateTimeTo: String? = null,
    ): Single<PageResponse<AlbumsResponse>>

    @GET(RoadCapturePathConstant.GET_ALBUM)
    fun getAlbumDetail(
        @Path("id") id: Long,
    ): Single<AlbumResponse>

    @GET(RoadCapturePathConstant.GET_FOLLOWERS_TO_ALBUMS)
    fun getFollowingAlbums(
        @Query("followingId") id: Long?,
        @Query("page") page: Int?,
        @Query("size") size: Int?,
        @Query("sort") sort: String?,
    ): Single<PageResponse<AlbumsResponse>>

    @GET(RoadCapturePathConstant.GET_USER_ALBUMS)
    fun getUserAlbums(
        @Query("page") page: Int?,
        @Query("size") size: Int?,
        @Query("placeCond.region1DepthName") region1DepthName: String?,
        @Query("placeCond.region2DepthName") region2DepthName: String?,
        @Query("placeCond.region3DepthName") region3DepthName: String?,
    ): Single<PageResponse<UserAlbumsResponse>>
}