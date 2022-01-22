package com.untilled.roadcapture.data.datasource.api.dto.address

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Address(
    val addressName: String,        // 지번 주소
    val roadAddressName: String? = null,   // 도로명 주소
    val region1DepthName: String,   // 시구명
    val region2DepthName: String,   // 시군구명
    val region3DepthName: String,   // 읍면동명
    val zoneNo: String              // 우편번호
): Parcelable
