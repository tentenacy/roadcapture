package com.untilled.roadcapture.data.datasource.api.dto.place

import android.os.Parcelable
import androidx.room.Embedded
import com.google.gson.annotations.SerializedName
import com.untilled.roadcapture.data.datasource.api.dto.address.Address
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlaceRequest(
    @SerializedName("createdAt")
    var placeCreatedAt: String,
    @SerializedName("lastModifiedAt")
    var placeLastModifiedAt: String,
    var latitude: Float,
    var longitude: Float,
    var name: String,
    @Embedded
    var address: Address
): Parcelable