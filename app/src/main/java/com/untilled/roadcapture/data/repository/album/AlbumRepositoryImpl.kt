package com.untilled.roadcapture.data.repository.album

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import com.untilled.roadcapture.data.datasource.api.RoadCaptureApi
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumResponse
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsCondition
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsResponse
import com.untilled.roadcapture.data.datasource.api.dto.comment.CommentsResponse
import com.untilled.roadcapture.data.datasource.api.dto.common.PageResponse
import com.untilled.roadcapture.data.datasource.paging.GetAlbumsRxPagingSource
import com.untilled.roadcapture.data.entity.AlbumsPage
import io.reactivex.rxjava3.core.Flowable
import retrofit2.Response
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlbumRepositoryImpl
@Inject constructor(
    private val api: RoadCaptureApi,
    private val pagingSource: GetAlbumsRxPagingSource,
) : AlbumRepository {


    override suspend fun getAlbumsTemp(
        page: Int?,
        size: Int?,
        dateTimeFrom: String?,
        dateTimeTo: String?
    ): Response<PageResponse<AlbumsResponse>> =
        api.getAlbumsTemp(page, dateTimeFrom, dateTimeTo)

    override suspend fun getAlbumCommentsList(albumsId: Int, page: Int?, size: Int?): Response<CommentsResponse> =
        api.getAlbumComments(albumsId, page, size)

    override suspend fun getAlbumDetail(id: Int): Response<AlbumResponse> =
        api.getAlbumDetail(id)

    override suspend fun getPictureCommentsList(
        pictureId: Int,
        page: Int?,
        size: Int?
    ): Response<CommentsResponse> =
        api.getPictureComments(pictureId,page,size)
}