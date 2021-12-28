package com.untilled.roadcapture.data.repository.albums

import com.untilled.roadcapture.data.response.albums.AlbumsResponse
import retrofit2.Response

interface AlbumsRepository {
    // todo: query 추가해야 함
    suspend fun getAlbumsList(
        page: Int? = null,
        size: Int? = null
    ): Response<AlbumsResponse>
}