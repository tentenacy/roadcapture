package com.untilled.roadcapture.data.dto.picture

import android.os.Parcelable
import com.untilled.roadcapture.data.dto.place.Place
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PictureResponse(
    val id: Int,
    val createdAt: String,
    val lastModifiedAt: String,
    val imageUrl: String,
    val description: String,
    val place: Place
) : Parcelable