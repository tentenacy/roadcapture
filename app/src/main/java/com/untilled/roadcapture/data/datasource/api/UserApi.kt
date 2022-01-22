package com.untilled.roadcapture.data.datasource.api

import com.untilled.roadcapture.data.datasource.api.dto.album.UserAlbumsResponse
import com.untilled.roadcapture.data.datasource.api.dto.common.PageResponse
import com.untilled.roadcapture.data.datasource.api.dto.user.*
import com.untilled.roadcapture.utils.constant.url.RoadCapturePathConstant
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.*

interface UserApi {
    @POST(RoadCapturePathConstant.POST_SOCIAL_LOGIN)
    fun socialLogin(
        @Path("socialType") socialType: String,
        @Body tokenRequest: TokenRequest,
    ): Single<Response<TokenResponse>>

    @POST(RoadCapturePathConstant.POST_SIGNUP)
    fun signup(
        @Body signupRequest: SignupRequest,
    ): Single<Response<Unit>>

    @POST(RoadCapturePathConstant.POST_SOCIAL_SIGNUP)
    fun socialSignup(
        @Path("socialType") socialType: String,
        @Body tokenRequest: TokenRequest,
    ): Single<Response<Unit>>

    @POST(RoadCapturePathConstant.POST_REISSUE)
    fun reissue(
        @Body reissueRequest: ReissueRequest
    ): Single<Response<TokenResponse>>

    @POST(RoadCapturePathConstant.POST_LOGIN)
    fun login(
        @Body loginRequest: LoginRequest
    ): Single<Response<TokenResponse>>

    @GET(RoadCapturePathConstant.GET_USER_DETAiL)
    fun getUserDetail(
    ): Single<UserResponse>

    @GET(RoadCapturePathConstant.GET_USER_INFO)
    fun getUserInfo(
        @Path("id") userId: Long,
    ): Single<UsersResponse>
}