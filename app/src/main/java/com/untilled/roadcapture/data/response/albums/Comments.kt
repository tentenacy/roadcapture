package com.untilled.roadcapture.data.response.albums

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Comments(
    val id: Int,
    val pictureId: Int,
    val createdAt : String,
    val lastModifiedAt: String,
    val content: String?,
    val user: User
) : Parcelable