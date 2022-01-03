package com.untilled.roadcapture.data.dto.comment

import android.os.Parcelable
import com.untilled.roadcapture.data.dto.user.Users
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Comments(
    val id: Int,
    val pictureId: Int,
    val createdAt : String,
    val lastModifiedAt: String,
    val content: String?,
    val users: Users
) : Parcelable