package com.untilled.roadcapture.data.datasource.api.dto.album

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.untilled.roadcapture.data.datasource.api.dto.picture.PictureResponse
import com.untilled.roadcapture.data.datasource.api.dto.user.UserResponse
import com.untilled.roadcapture.data.datasource.api.dto.user.UsersResponse
import com.untilled.roadcapture.data.entity.User
import kotlinx.android.parcel.Parcelize
import java.time.LocalDate
import java.time.LocalDateTime

@Parcelize
data class AlbumsResponse(
    val id: Long,
    val createdAt: String,
    val lastModifiedAt: String,
    val title: String,
    val description: String,
    val thumbnailUrl: String,
    val user: UsersResponse,
    val viewCount: Int,
    val likeCount: Int,
    val commentCount: Int,
    val liked: Boolean
) : Parcelable