package com.untilled.roadcapture.data.datasource.api

import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsResponse
import com.untilled.roadcapture.data.datasource.api.dto.common.PageResponse
import com.untilled.roadcapture.data.datasource.api.dto.user.UsersResponse
import com.untilled.roadcapture.utils.constant.url.RoadCapturePathConstant
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface FollowApi {
    @POST(RoadCapturePathConstant.POST_FOLLOWERS_FOLLOW)
    fun follow(
        @Path("toUserId") toUserId: Long,
    ): Single<Unit>

    @DELETE(RoadCapturePathConstant.DELETE_FOLLOWERS_FOLLOW)
    fun unfollow(
        @Path("toUserId") toUserId: Long,
    ): Single<Unit>

    @GET(RoadCapturePathConstant.GET_USER_FOLLOWER)
    fun getUserFollowers(
        @Path("userId") id: Long?,
        @Query("page") page: Int?,
        @Query("size") size: Int?,
        @Query("sort") sort: String? = null,
        @Query("username") username: String?
    ): Single<PageResponse<UsersResponse>>

    @GET(RoadCapturePathConstant.GET_USER_FOLLOWING)
    fun getUserFollowings(
        @Path("userId") id: Long?,
        @Query("page") page: Int?,
        @Query("size") size: Int?,
        @Query("sort") sort: String? = null,
        @Query("username") username: String?
    ): Single<PageResponse<UsersResponse>>

    @GET(RoadCapturePathConstant.GET_MY_FOLLOWER)
    fun getFollowers(
        @Query("username") username: String?,
        @Query("page") page: Int?,
        @Query("size") size: Int?,
        @Query("sort") sort: String? = null,
    ): Single<PageResponse<UsersResponse>>

    @GET(RoadCapturePathConstant.GET_MY_FOLLOWING)
    fun getFollowings(
        @Query("username") username: String?,
        @Query("page") page: Int?,
        @Query("size") size: Int?,
        @Query("sort") sort: String? = null,
    ): Single<PageResponse<UsersResponse>>
}