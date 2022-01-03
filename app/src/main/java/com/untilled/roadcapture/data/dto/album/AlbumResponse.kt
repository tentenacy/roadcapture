package com.untilled.roadcapture.data.dto.album

import android.os.Parcelable
import com.untilled.roadcapture.data.dto.picture.PictureResponse
import com.untilled.roadcapture.data.entity.User
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AlbumResponse(
    val id: Int,
    val createdAt: String?,
    val lastModifiedAt: String,
    val title: String,
    val description: String,
    val thumbnailUrl: String,
    val user: User,
    val viewCount: String,
    val likeCount: String,
    val commentCount: String,
    val pictureResponses: List<PictureResponse>
) : Parcelable