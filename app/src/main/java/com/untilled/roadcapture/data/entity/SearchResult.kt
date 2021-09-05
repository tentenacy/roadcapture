package com.untilled.roadcapture.data.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchResult(
    @SerializedName("name")
    var name: String,
    @SerializedName("address_number")
    var addressNumber: String,
    @SerializedName("roadname")
    var roadName: String,
    @SerializedName("location_lat_lng")
    var locationLatLng: LocationLatLng
) : Parcelable