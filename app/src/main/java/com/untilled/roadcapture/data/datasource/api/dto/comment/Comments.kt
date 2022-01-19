package com.untilled.roadcapture.data.datasource.api.dto.comment

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.untilled.roadcapture.data.datasource.api.dto.user.UsersResponse
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Comments(
    val id: Int,
    val pictureId: Int,
    var createdAt : LocalDateTime?,
    val lastModifiedAt: LocalDateTime?,
    val content: String?,
    @SerializedName("user")
    val user: UsersResponse
) : Parcelable