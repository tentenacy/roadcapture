package com.untilled.roadcapture.data.entity

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchResult(
    @SerializedName("name")
    var name: String,
    @SerializedName("address_number")
    var addressName: String,
    @SerializedName("roadname")
    var roadAddressName: String,
    @SerializedName("location_lat_lng")
    @Embedded var locationLatLng: LocationLatLng,
    var region1DepthName: String,   // 시구명
    var region2DepthName: String,   // 시군구명
    var region3DepthName: String,   // 읍면동명
    var zoneNo: String              // 우편번호
) : Parcelable