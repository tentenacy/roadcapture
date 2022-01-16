package com.untilled.roadcapture.data.datasource.api

import com.untilled.roadcapture.data.datasource.api.dto.user.ReissueRequest
import com.untilled.roadcapture.data.datasource.api.dto.user.TokenRequest
import com.untilled.roadcapture.data.datasource.api.dto.user.TokenResponse
import com.untilled.roadcapture.data.datasource.api.dto.user.Users
import com.untilled.roadcapture.data.entity.User
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

    @POST(RoadCapturePathConstant.POST_SOCIAL_SIGNUP)
    fun socialSignup(
        @Path("socialType") socialType: String,
        @Body tokenRequest: TokenRequest,
    ): Single<Response<Unit>>

    @POST(RoadCapturePathConstant.POST_REISSUE)
    fun reissue(
        @Body reissueRequest: ReissueRequest
    ): Single<Response<TokenResponse>>

    @GET(RoadCapturePathConstant.GET_USER_DETAiL)
    fun getUserDetail(
        @Header("X-AUTH-TOKEN") token: String
    ): Single<User>
}