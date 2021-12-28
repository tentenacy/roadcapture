package com.untilled.roadcapture.data.response.albums

import android.os.Parcelable
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
    val user: User,
    val viewCount: Int
) : Parcelable