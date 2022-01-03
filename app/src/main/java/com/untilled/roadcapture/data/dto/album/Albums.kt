package com.untilled.roadcapture.data.dto.album

import android.os.Parcelable
import android.util.Log
import com.google.gson.annotations.SerializedName
import com.untilled.roadcapture.data.dto.user.Users
import com.untilled.roadcapture.utils.dateToSnsFormat
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