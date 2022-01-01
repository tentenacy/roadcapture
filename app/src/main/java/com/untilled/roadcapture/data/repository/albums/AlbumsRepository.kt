package com.untilled.roadcapture.data.repository.albums

import com.untilled.roadcapture.data.entity.Album
import com.untilled.roadcapture.data.response.albums.AlbumsResponse
import com.untilled.roadcapture.data.response.albums.CommentsResponse
import retrofit2.Response
import retrofit2.http.Path
import retrofit2.http.Query

interface AlbumsRepository {
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
    ): Response<Album>

}