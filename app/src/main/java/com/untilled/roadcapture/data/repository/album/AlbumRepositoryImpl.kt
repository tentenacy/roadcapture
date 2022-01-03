package com.untilled.roadcapture.data.repository.album

import com.untilled.roadcapture.data.api.RoadCaptureService
import com.untilled.roadcapture.data.dto.album.AlbumResponse
import com.untilled.roadcapture.data.dto.album.AlbumsResponse
import com.untilled.roadcapture.data.dto.comment.CommentsResponse
import retrofit2.Response
import javax.inject.Inject

class AlbumRepositoryImpl
@Inject constructor(
    private val service: RoadCaptureService
) : AlbumRepository {
    // todo: query 추가해야 함
    override suspend fun getAlbumsList(page: Int?, size: Int?): Response<AlbumsResponse> =
        service.getAlbumsList(page?.toString(), size?.toString())

    override suspend fun getCommentsList(albumsId: String): Response<CommentsResponse> =
        service.getCommentsList(albumsId)

    override suspend fun getAlbumDetail(id: String): Response<AlbumResponse> =
        service.getAlbumDetail(id)

}