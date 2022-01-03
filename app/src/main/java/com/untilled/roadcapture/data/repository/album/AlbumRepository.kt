package com.untilled.roadcapture.data.repository.album

import com.untilled.roadcapture.data.dto.album.AlbumResponse
import com.untilled.roadcapture.data.dto.album.AlbumsResponse
import com.untilled.roadcapture.data.dto.comment.CommentsResponse
import retrofit2.Response
import retrofit2.http.Path

interface AlbumRepository {
    // todo: query 추가해야 함
    suspend fun getAlbumsList(
        page: Int? = null,
        size: Int? = null
    ): Response<AlbumsResponse>

    suspend fun getCommentsList(
        @Path("albumId") albumId: String
    ): Response<CommentsResponse>

    suspend fun getAlbumDetail(
        @Path("id") id: String
    ): Response<AlbumResponse>

}