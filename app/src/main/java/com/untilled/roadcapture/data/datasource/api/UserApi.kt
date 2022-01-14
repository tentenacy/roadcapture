package com.untilled.roadcapture.data.datasource.api

import com.untilled.roadcapture.data.datasource.api.dto.user.SocialRequest
import com.untilled.roadcapture.data.datasource.api.dto.user.SocialLoginResponse
import com.untilled.roadcapture.utils.constant.url.RoadCaptureUrl
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApi {
    @POST(RoadCaptureUrl.POST_SOCIAL_LOGIN)
    fun socialLogin(
        @Path("socialType") socialType: String,
        @Body socialRequest: SocialRequest,
    ): Single<SocialLoginResponse>

    @POST(RoadCaptureUrl.POST_SOCIAL_SIGNUP)
    fun socialSignup(
        @Path("socialType") socialType: String,
        @Body socialRequest: SocialRequest,
    ): Single<Response<Unit>>
}