package com.untilled.roadcapture.data.repository.album

import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumResponse
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsResponse
import com.untilled.roadcapture.data.datasource.api.dto.comment.CommentsResponse
import com.untilled.roadcapture.data.datasource.api.dto.common.PageResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.Path
import retrofit2.http.Query

interface AlbumRepository {

    suspend fun getAlbumsTemp(
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("dateTimeFrom")dateTimeFrom: String?,
        @Query("dateTimeTo")dateTimeTo: String?
    ): Response<PageResponse<AlbumsResponse>>

    fun getAlbumCommentsList(
        @Path("albumId") albumId: Long,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null
    ): Single<PageResponse<CommentsResponse>>

    suspend fun getAlbumDetail(
        @Path("id") id: Int
    ): Response<AlbumResponse>

    fun getPictureCommentsList(
        @Path("pictureId") pictureId: Long,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null
    ): Single<PageResponse<CommentsResponse>>

}