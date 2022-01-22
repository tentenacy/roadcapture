package com.untilled.roadcapture.data.datasource.api

import com.untilled.roadcapture.BuildConfig
import com.untilled.roadcapture.data.datasource.api.dto.LikeApi
import retrofit2.Retrofit
import retrofit2.create

interface RoadCaptureApi: AlbumApi, CommentApi, UserApi, FollowApi, LikeApi {

    companion object {
        fun create(retrofitBuilder: Retrofit.Builder): RoadCaptureApi {
            return retrofitBuilder
                .baseUrl(BuildConfig.API_URL_BASE)
                .build()
                .create()
        }
    }
}