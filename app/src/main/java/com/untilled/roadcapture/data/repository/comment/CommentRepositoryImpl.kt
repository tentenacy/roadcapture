package com.untilled.roadcapture.data.repository.comment

import com.untilled.roadcapture.data.datasource.api.RoadCaptureApi
import com.untilled.roadcapture.data.datasource.api.dto.comment.CommentCreateRequest
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class CommentRepositoryImpl @Inject constructor(
    private val roadCaptureApi: RoadCaptureApi
): CommentRepository {

    override fun postPictureComment(pictureId: Long, request: CommentCreateRequest): Single<Unit> =
        roadCaptureApi.postPictureComment(pictureId, request)
            .subscribeOn(Schedulers.io())
}