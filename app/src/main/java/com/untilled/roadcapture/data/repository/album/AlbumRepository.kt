package com.untilled.roadcapture.data.repository.album

import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumResponse
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsResponse
import com.untilled.roadcapture.data.datasource.api.dto.comment.CommentsResponse
import com.untilled.roadcapture.data.datasource.api.dto.common.PageRequest
import com.untilled.roadcapture.data.datasource.api.dto.common.PageResponse
import com.untilled.roadcapture.utils.constant.url.RoadCapturePathConstant
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AlbumRepository {
    suspend fun getAlbumsTemp( page: Int? = null, size: Int? = null, dateTimeFrom: String?, dateTimeTo: String?): Response<PageResponse<AlbumsResponse>>
    fun getAlbumCommentsList(albumId: Long, page: Int? = null, size: Int? = null): Single<PageResponse<CommentsResponse>>
    fun getAlbumDetail(id: Long): Single<AlbumResponse>
    fun getPictureCommentsList(pictureId: Long, page: Int? = null, size: Int? = null): Single<PageResponse<CommentsResponse>>
    fun likesAlbum(albumId: Long): Single<Unit>
    fun unlikesAlbum(albumId: Long): Single<Unit>
}