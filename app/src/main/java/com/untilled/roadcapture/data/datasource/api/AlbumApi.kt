package com.untilled.roadcapture.data.datasource.api

import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumResponse
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsResponse
import com.untilled.roadcapture.data.datasource.api.dto.common.PageResponse
import com.untilled.roadcapture.utils.constant.url.RoadCapturePathConstant
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AlbumApi {

    @GET(RoadCapturePathConstant.GET_ALBUMS)
    // todo: query 추가해야 함
    fun getAlbumsTemp(
        @Query("page") page: Int? = null,
        @Query("dateTimeFrom") dateTimeFrom: String? = null,
        @Query("dateTimeTo") dateTimeTo: String? = null,
    ): Response<PageResponse<AlbumsResponse>>

    @GET(RoadCapturePathConstant.GET_ALBUMS)
    // todo: query 추가해야 함
    fun getAlbums(
        @Query("page") page: Int? = null,
        @Query("dateTimeFrom") dateTimeFrom: String? = null,
        @Query("dateTimeTo") dateTimeTo: String? = null,
    ): Single<PageResponse<AlbumsResponse>>

    @GET(RoadCapturePathConstant.GET_ALBUM)
    fun getAlbumDetail(
        @Path("id") id: Int
    ): Response<AlbumResponse>
}