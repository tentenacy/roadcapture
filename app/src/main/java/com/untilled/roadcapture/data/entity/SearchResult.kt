package com.untilled.roadcapture.data.entity

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchResult(
    @SerializedName("name")
    var placeName: String,
    @SerializedName("address_number")
    var addressNumber: String,
    @SerializedName("roadname")
    var roadName: String,
    @SerializedName("location_lat_lng")
    @Embedded var locationLatLng: LocationLatLng
) : Parcelable