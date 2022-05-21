package com.untilled.roadcapture.data.repository.album

import com.untilled.roadcapture.data.datasource.api.RoadCaptureApi
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumResponse
import com.untilled.roadcapture.utils.retryThreeTimes
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
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
        roadCaptureApi.getAlbumDetail(id)

    override fun postAlbum(images: List<MultipartBody.Part>, data: String): Single<Unit> =
        roadCaptureApi.postAlbum(images, data)

    override fun deleteAlbum(albumId: Long): Single<Unit> = roadCaptureApi.deleteAlbum(albumId)
}