package com.untilled.roadcapture.data.datasource.api

import com.untilled.roadcapture.data.datasource.api.dto.user.TokenResponse
import com.untilled.roadcapture.utils.constant.url.RoadCapturePathConstant
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface FollowApi {
    @POST(RoadCapturePathConstant.POST_FOLLOWERS_FOLLOW)
    fun follow(
        @Path("toUserId") id: Int,
        @Header("X-AUTH-TOKEN") token: String
    ): Single<Unit>
}