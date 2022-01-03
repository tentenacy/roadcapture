package com.untilled.roadcapture.data.dto.album

import android.os.Parcelable
import com.untilled.roadcapture.data.dto.user.Users
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Albums(
    val commentCount: Int,
    val createdAt: String,
    val description: String,
    val id: Int,
    val lastModifiedAt: String,
    val likeCount: Int,
    val thumbnailUrl: String,
    val title: String,
    val users: Users?,
    val viewCount: Int
) : Parcelable