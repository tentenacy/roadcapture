package com.untilled.roadcapture.data.datasource.api.dto.album

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.untilled.roadcapture.data.datasource.api.dto.user.Users
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Albums(
    val commentCount: Int,
    var createdAt: String,
    val description: String,
    val id: Int,
    val lastModifiedAt: String,
    val likeCount: Int,
    val thumbnailUrl: String,
    val title: String,
    @SerializedName("user")
    val users: Users?,
    val viewCount: Int
) : Parcelable