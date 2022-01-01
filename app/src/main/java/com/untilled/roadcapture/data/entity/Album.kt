package com.untilled.roadcapture.data.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Album(
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
    val pictures: List<Picture>
) : Parcelable