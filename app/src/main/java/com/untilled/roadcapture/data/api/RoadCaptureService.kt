package com.untilled.roadcapture.data.api

import com.untilled.roadcapture.BuildConfig
import com.untilled.roadcapture.data.dto.album.AlbumResponse
import com.untilled.roadcapture.data.dto.album.AlbumsResponse
import com.untilled.roadcapture.data.dto.comment.CommentsResponse
import com.untilled.roadcapture.data.url.RoadCaptureUrl.GET_ALBUMS
import com.untilled.roadcapture.data.url.RoadCaptureUrl.ROAD_CAPTURE_BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface RoadCaptureService {
    @GET(GET_ALBUMS)
    // todo: query 추가해야 함
    suspend fun getAlbumsList(
        @Header("X-AUTH-TOKEN") token: String,
        @Query("page") page: String? = null,
        @Query("size") size: String? = null,
        @Query("dateTimeFrom") dateTimeFrom: String,
        @Query("dateTimeTo")dateTimeTo: String
    ): Response<AlbumsResponse>

    @GET("albums/{albumId}/pictures/comments")
    suspend fun getAlbumCommentsList(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("albumId") albumId: Int,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
    ): Response<CommentsResponse>

    @GET("albums/{id}")
    suspend fun getAlbumDetail(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("id") id: String
    ): Response<AlbumResponse>

    @GET("pictures/{pictureId}/comments")
    suspend fun getPictureCommentsList(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("pictureId") pictureId: Int,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null
    ) : Response<CommentsResponse>

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