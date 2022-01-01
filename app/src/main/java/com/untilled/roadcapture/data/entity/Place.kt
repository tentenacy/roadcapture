package com.untilled.roadcapture.data.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Place(
    val id: Int,
    val name: String,
    val latitude: String,
    val longitude: String,
    val address: Address
): Parcelable
