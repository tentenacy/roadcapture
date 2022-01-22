package com.untilled.roadcapture.data.datasource.api.dto

import com.untilled.roadcapture.utils.constant.url.RoadCapturePathConstant
import io.reactivex.rxjava3.core.Single
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

interface LikeApi {
    @POST(RoadCapturePathConstant.POST_ALBUM_LIKE)
    fun likeAlbum(
        @Path("albumId") albumId: Long
    ): Single<Unit>

    @DELETE(RoadCapturePathConstant.DELETE_ALBUM_LIKE)
    fun unlikeAlbum(
        @Path("albumId") albumId: Long
    ): Single<Unit>
}