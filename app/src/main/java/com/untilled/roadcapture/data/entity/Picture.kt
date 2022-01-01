package com.untilled.roadcapture.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Picture(
    val id: Int,
    val createdAt: String,
    val lastModifiedAt: String,
    val imageUrl: String,
    val description: String,
    val place: Place
) : Parcelable