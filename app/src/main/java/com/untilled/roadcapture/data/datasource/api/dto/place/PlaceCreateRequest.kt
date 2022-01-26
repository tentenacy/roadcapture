package com.untilled.roadcapture.data.datasource.api.dto.place

import android.os.Parcelable
import androidx.room.Embedded
import com.google.gson.annotations.SerializedName
import com.untilled.roadcapture.data.datasource.api.dto.address.Address
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlaceCreateRequest(
    @SerializedName("createdAt")
    var placeCreatedAt: String,
    @SerializedName("lastModifiedAt")
    var placeLastModifiedAt: String,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    @Embedded
    val address: Address
): Parcelable