package com.untilled.roadcapture.data.datasource.api.dto.place

import android.os.Parcelable
import androidx.room.Embedded
import com.google.gson.annotations.SerializedName
import com.untilled.roadcapture.data.datasource.api.dto.address.Address
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlaceRequest(
    @SerializedName("createdAt")
    val placeCreatedAt: String,
    @SerializedName("lastModifiedAt")
    val placeLastModifiedAt: String,
    val latitude: Float,
    val longitude: Float,
    val name: String,
    @Embedded
    val address: Address
): Parcelable