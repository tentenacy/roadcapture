package com.untilled.roadcapture.data.datasource.api

import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumResponse
import com.untilled.roadcapture.data.datasource.api.dto.common.PageResponse
import com.untilled.roadcapture.utils.constant.url.RoadCapturePathConstant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AlbumApi {

    @GET(RoadCapturePathConstant.GET_ALBUMS)
    // todo: query 추가해야 함
    suspend fun getAlbums(
        @Query("page") page: String? = null,
        @Query("size") size: String? = null,
        @Query("dateTimeFrom") dateTimeFrom: String?,
        @Query("dateTimeTo") dateTimeTo: String?
    ): Response<PageResponse<AlbumResponse>>

    @GET(RoadCapturePathConstant.GET_ALBUM)
    suspend fun getAlbum(
        @Path("id") id: String
    ): Response<AlbumResponse>
}