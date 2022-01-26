package com.untilled.roadcapture.data.datasource.api

import com.untilled.roadcapture.data.datasource.api.dto.common.PageResponse
import com.untilled.roadcapture.data.datasource.api.dto.follower.FollowersResponse
import com.untilled.roadcapture.data.datasource.api.dto.follower.FollowingsSortByAlbumResponse
import com.untilled.roadcapture.data.datasource.api.dto.user.StudioUserResponse
import com.untilled.roadcapture.data.datasource.api.dto.user.UsersResponse
import com.untilled.roadcapture.utils.constant.url.RoadCapturePathConstant
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface FollowerApi {
    @POST(RoadCapturePathConstant.POST_FOLLOWERS)
    fun follow(
        @Path("toUserId") toUserId: Long,
    ): Single<Unit>

    @DELETE(RoadCapturePathConstant.DELETE_FOLLOWERS)
    fun unfollow(
        @Path("toUserId") toUserId: Long,
    ): Single<Unit>

    @GET(RoadCapturePathConstant.GET_USER_FOLLOWERS)
    fun getUserFollowers(
        @Path("userId") id: Long?,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("sort") sort: String? = "createdAt,desc",
        @Query("username") username: String?
    ): Single<PageResponse<FollowersResponse>>

    @GET(RoadCapturePathConstant.GET_USER_FOLLOWINGS)
    fun getUserFollowings(
        @Path("userId") id: Long?,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("sort") sort: String? = "createdAt,desc",
        @Query("username") username: String?
    ): Single<PageResponse<UsersResponse>>

    @GET(RoadCapturePathConstant.GET_FOLLOWERS)
    fun getFollowers(
        @Query("username") username: String?,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("sort") sort: String? = "createdAt,desc",
    ): Single<PageResponse<FollowersResponse>>

    @GET(RoadCapturePathConstant.GET_FOLLOWINGS)
    fun getFollowings(
        @Query("username") username: String?,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("sort") sort: String? = "createdAt,desc",
    ): Single<PageResponse<UsersResponse>>

    @GET(RoadCapturePathConstant.GET_FOLLOWINGS_SORT_BY_ALBUM)
    fun getFollowingsSortByAlbum(
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("sort") sort: String? = "createdAt,desc"
    ): Single<PageResponse<FollowingsSortByAlbumResponse>>

}