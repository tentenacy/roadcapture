package com.untilled.roadcapture.data.datasource.api.dto.comment

import android.os.Parcelable
import androidx.room.Embedded
import com.google.gson.annotations.SerializedName
import com.untilled.roadcapture.data.datasource.api.dto.common.Pageable
import com.untilled.roadcapture.data.datasource.api.dto.common.Sort
import com.untilled.roadcapture.data.datasource.api.dto.user.UsersResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CommentsResponse(
    val id: Long,
    val pictureId: Long,
    val createdAt: String,
    val lastModifiedAt: String,
    val content: String,
    @Embedded
    val user: UsersResponse
) : Parcelable