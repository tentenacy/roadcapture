package com.untilled.roadcapture.data.datasource.api.dto.comment

import android.os.Parcelable
import androidx.room.Embedded
import com.untilled.roadcapture.data.datasource.api.dto.user.UsersResponse
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime

@Parcelize
data class CommentsResponse(
    val id: Long,
    val pictureId: Long,
    val createdAt: LocalDateTime,
    val lastModifiedAt: LocalDateTime,
    val content: String,
    @Embedded
    val user: UsersResponse
) : Parcelable