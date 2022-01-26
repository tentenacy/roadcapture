package com.untilled.roadcapture.data.datasource.api.dto.album

import android.os.Parcelable
import com.untilled.roadcapture.data.datasource.api.dto.picture.PictureResponse
import com.untilled.roadcapture.data.datasource.api.dto.user.AlbumUserResponse
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime

@Parcelize
data class AlbumResponse(
    val id: Long,
    val createdAt: LocalDateTime,
    val lastModifiedAt: LocalDateTime,
    val title: String,
    val description: String = "",
    val user: AlbumUserResponse,
    val viewCount: Int,
    val likeCount: Int,
    val commentCount: Int,
    val liked: Boolean,
    val pictures: List<PictureResponse>
) : Parcelable
