package com.untilled.roadcapture.data.api

import com.untilled.roadcapture.BuildConfig
import com.untilled.roadcapture.data.response.albums.AlbumsResponse
import com.untilled.roadcapture.data.response.albums.CommentsResponse
import com.untilled.roadcapture.data.url.RoadCaptureUrl.GET_ALBUMS
import com.untilled.roadcapture.data.url.RoadCaptureUrl.ROAD_CAPTURE_BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

interface RoadCaptureService {
    @GET(GET_ALBUMS)
    // todo: query 추가해야 함
    suspend fun getAlbumsList(
        @Query("page") page: String? = null,
        @Query("size") size: String? = null
    ): Response<AlbumsResponse>

    @GET(GET_ALBUMS)
    // todo: query 추가해야 함
    suspend fun getCommentsList(
        @Query("albumsId") albumsId: String
    ): Response<CommentsResponse>

    companion object {
        fun create(): RoadCaptureService {
            val logger = HttpLoggingInterceptor().apply {
                level = if(BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(ROAD_CAPTURE_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create()
        }
    }
}