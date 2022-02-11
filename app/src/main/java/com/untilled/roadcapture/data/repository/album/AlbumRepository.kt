package com.untilled.roadcapture.data.repository.album

import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumResponse
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody

interface AlbumRepository {
    fun getAlbumDetail(id: Long): Single<AlbumResponse>
    fun likeAlbum(albumId: Long): Single<Unit>
    fun unlikeAlbum(albumId: Long): Single<Unit>
    fun postAlbum(images: List<MultipartBody.Part>, data: String): Single<Unit>
    fun deleteAlbum(albumId: Long): Single<Unit>
}