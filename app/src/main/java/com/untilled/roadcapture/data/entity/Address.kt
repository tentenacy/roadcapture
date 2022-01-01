package com.untilled.roadcapture.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Address(
    val addressName: String,
    var roadAddressName: String?,
    val region1DepthName: String,
    val region2DepthName: String,
    val region3DepthName: String,
    val zoneNo: String
):Parcelable
