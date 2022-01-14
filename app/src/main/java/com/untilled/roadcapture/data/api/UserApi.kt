package com.untilled.roadcapture.data.api

import com.untilled.roadcapture.data.api.dto.user.SocialRequest
import com.untilled.roadcapture.data.api.dto.user.SocialLoginResponse
import io.reactivex.rxjava3.core.Single
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApi {
    @POST("users/social/{socialType}/token")
    fun socialLogin(
        @Path("socialType") socialType: String,
        @Body socialRequest: SocialRequest,
    ): Single<SocialLoginResponse>

    @POST("users/social/{socialType}")
    fun socialSignup(
        @Path("socialType") socialType: String,
        @Body socialRequest: SocialRequest,
    ): Single<Response<Unit>>
}