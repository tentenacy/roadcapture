package com.untilled.roadcapture.data.datasource.api

import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumResponse
import com.untilled.roadcapture.data.datasource.api.dto.common.PageResponse
import com.untilled.roadcapture.utils.constant.url.RoadCapturePathConstant
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface FollowApi {
    @POST(RoadCapturePathConstant.POST_FOLLOWERS_FOLLOW)
    fun follow(
        @Path("toUserId") id: Int,
    ): Single<Unit>

    @GET(RoadCapturePathConstant.GET_FOLLOWERS_TO_ALBUMS)
    fun getFollowingAlbums(
        @Query("page") page: Int?,
        @Query("size") size: Int?,
        @Query("sort") sort: String?,
        @Query("followingId") id: Int?,
    ): Single<PageResponse<AlbumResponse>>
}