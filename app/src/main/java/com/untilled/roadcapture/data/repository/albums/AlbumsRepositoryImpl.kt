package com.untilled.roadcapture.data.repository.albums

import com.untilled.roadcapture.data.api.RoadCaptureService
import com.untilled.roadcapture.data.entity.Album
import com.untilled.roadcapture.data.response.albums.AlbumsResponse
import com.untilled.roadcapture.data.response.albums.CommentsResponse
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

class AlbumsRepositoryImpl
@Inject constructor(
    private val service: RoadCaptureService
) : AlbumsRepository {
    // todo: query 추가해야 함
    override suspend fun getAlbumsList(page: Int?, size: Int?): Response<AlbumsResponse> =
        service.getAlbumsList(page?.toString(), size?.toString())

    override suspend fun getCommentsList(albumsId: String): Response<CommentsResponse> =
        service.getCommentsList(albumsId)

    override suspend fun getAlbumDetail(id: String): Response<Album> =
        service.getAlbumDetail(id)

}