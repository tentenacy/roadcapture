package com.untilled.roadcapture.data.dto.address

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Address(
    var addressName: String,        // 지번 주소
    var roadAddressName: String?,   // 도로명 주소
    var region1DepthName: String,   // 시구명
    var region2DepthName: String,   // 시군구명
    var region3DepthName: String,   // 읍면동명
    var zoneNo: String              // 우편번호
):Parcelable
