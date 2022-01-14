package com.untilled.roadcapture.data.api

import com.untilled.roadcapture.data.api.dto.album.AlbumResponse
import com.untilled.roadcapture.data.api.dto.album.AlbumsResponse
import com.untilled.roadcapture.data.url.RoadCaptureUrl
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface AlbumApi {

    @GET(RoadCaptureUrl.GET_ALBUMS)
    // todo: query 추가해야 함
    suspend fun getAlbumsList(
        @Header("X-AUTH-TOKEN") token: String,
        @Query("page") page: String? = null,
        @Query("size") size: String? = null,
        @Query("dateTimeFrom") dateTimeFrom: String?,
        @Query("dateTimeTo") dateTimeTo: String?
    ): Response<AlbumsResponse>

    @GET("albums/{id}")
    suspend fun getAlbumDetail(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("id") id: String
    ): Response<AlbumResponse>
}