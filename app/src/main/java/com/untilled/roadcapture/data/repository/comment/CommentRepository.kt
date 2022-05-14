package com.untilled.roadcapture.data.repository.comment

import com.untilled.roadcapture.data.datasource.api.dto.comment.CommentCreateRequest
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface CommentRepository {
    fun postPictureComment(pictureId: Long, request: CommentCreateRequest): Single<Unit>
    fun deleteComment(commentId: Long): Completable
}