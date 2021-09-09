package com.untilled.roadcapture.data.entity

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LocationLatLng(
    val latitude: Float,
    val longitude: Float
): Parcelable

