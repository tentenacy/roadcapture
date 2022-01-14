package com.untilled.roadcapture.data.datasource.api

import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumResponse
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsResponse
import com.untilled.roadcapture.utils.constant.url.RoadCaptureUrl
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface AlbumApi {

    @GET(RoadCaptureUrl.GET_ALBUMS)
    // todo: query 추가해야 함
    suspend fun getAlbums(
        @Header("X-AUTH-TOKEN") token: String,
        @Query("page") page: String? = null,
        @Query("size") size: String? = null,
        @Query("dateTimeFrom") dateTimeFrom: String?,
        @Query("dateTimeTo") dateTimeTo: String?
    ): Response<AlbumsResponse>

    @GET(RoadCaptureUrl.GET_ALBUM)
    suspend fun getAlbum(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("id") id: String
    ): Response<AlbumResponse>
}