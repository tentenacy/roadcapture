package com.untilled.roadcapture.data.datasource.api.dto.comment

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.untilled.roadcapture.data.datasource.api.dto.user.Users
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Comments(
    val id: Int,
    val pictureId: Int,
    var createdAt : String,
    val lastModifiedAt: String,
    val content: String?,
    @SerializedName("user")
    val user: Users
) : Parcelable