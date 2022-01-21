package com.untilled.roadcapture.data.datasource.api.dto.album

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.untilled.roadcapture.data.datasource.api.dto.picture.PictureResponse
import com.untilled.roadcapture.data.datasource.api.dto.user.UsersResponse
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime

@Parcelize
data class AlbumResponse(
    val id: Long,
    var createdAt: LocalDateTime?,
    val lastModifiedAt: LocalDateTime?,
    val title: String,
    val description: String?,
    val user: UsersResponse,
    val viewCount: Int,
    val likeCount: Int,
    val commentCount: Int,
    val liked: Boolean,
    val pictures: List<PictureResponse>? = null
) : Parcelable
