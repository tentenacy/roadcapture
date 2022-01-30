package com.untilled.roadcapture.data.repository.album

import androidx.paging.PagingSource
import com.untilled.roadcapture.data.datasource.api.RoadCaptureApi
import com.untilled.roadcapture.data.datasource.api.dto.address.PlaceCondition
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumCreateRequest
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumResponse
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsResponse
import com.untilled.roadcapture.data.datasource.api.dto.album.UserAlbumsResponse
import com.untilled.roadcapture.data.datasource.api.dto.comment.CommentsResponse
import com.untilled.roadcapture.data.datasource.api.dto.common.PageRequest
import com.untilled.roadcapture.data.datasource.api.dto.common.PageResponse
import com.untilled.roadcapture.utils.applyRetryPolicy
import com.untilled.roadcapture.utils.constant.policy.RetryPolicyConstant
import com.untilled.roadcapture.utils.retryThreeTimes
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlbumRepositoryImpl
@Inject constructor(
    private val roadCaptureApi: RoadCaptureApi,
) : AlbumRepository {

    override fun likeAlbum(albumId: Long): Single<Unit> = roadCaptureApi.likeAlbum(albumId)

    override fun unlikeAlbum(albumId: Long): Single<Unit> = roadCaptureApi.unlikeAlbum(albumId)

    override fun getAlbumDetail(id: Long): Single<AlbumResponse> =
        roadCaptureApi.getAlbumDetail(id).retryThreeTimes()

    override fun postAlbum(images: List<MultipartBody.Part>, data: String): Single<Unit> =
        roadCaptureApi.postAlbum(images, data)
}