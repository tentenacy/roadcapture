package com.untilled.roadcapture.data.api

import com.untilled.roadcapture.BuildConfig
import com.untilled.roadcapture.data.api.dto.user.SocialLoginResponse
import com.untilled.roadcapture.data.api.dto.user.SocialRequest
import io.reactivex.rxjava3.core.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface RoadCaptureApi: AlbumApi, CommentApi, UserApi {

    companion object {
        fun create(retrofitBuilder: Retrofit.Builder): RoadCaptureApi {
            return retrofitBuilder
                .baseUrl(BuildConfig.API_URL_BASE)
                .build()
                .create()
        }
    }
}