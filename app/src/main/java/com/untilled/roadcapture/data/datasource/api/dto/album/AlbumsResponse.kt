package com.untilled.roadcapture.data.datasource.api.dto.album

import android.os.Parcelable
import com.untilled.roadcapture.data.datasource.api.dto.picture.ThumbnailPictureResponse
import com.untilled.roadcapture.data.datasource.api.dto.user.StudioUserResponse
import com.untilled.roadcapture.data.datasource.api.dto.user.UsersResponse
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime

@Parcelize
data class AlbumsResponse(
    val id: Long,
    val createdAt: LocalDateTime,
    val lastModifiedAt: LocalDateTime,
    val title: String,
    val description: String = "",
    val thumbnailPicture: ThumbnailPictureResponse,
    val user: UsersResponse,
    val viewCount: Int,
    val likeCount: Int,
    val commentCount: Int,
    val liked: Boolean
) : Parcelable