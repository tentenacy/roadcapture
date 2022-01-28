package com.untilled.roadcapture.data.entity

import android.os.Parcelable
import com.untilled.roadcapture.data.datasource.api.dto.address.Address
import com.untilled.roadcapture.data.datasource.api.dto.place.PlaceCreateRequest
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Place(
    val latitude: Double,   // 위도
    val longitude: Double,  // 경도
    val name: String,       // 장소 이름
    val addressName: String,        // 지번 주소
    val roadAddressName: String,   // 도로명 주소
    val region1DepthName: String,   // 시구명
    val region2DepthName: String,   // 시군구명
    val region3DepthName: String,   // 읍면동명
    val zoneNo: String = ""              // 우편번호
) : Parcelable {
    fun toPlaceCreateRequest(): PlaceCreateRequest =
        PlaceCreateRequest(
            latitude = latitude,
            longitude = longitude,
            name = name,
            address = Address(
                addressName = addressName,
                roadAddressName = roadAddressName,
                region1DepthName = region1DepthName,
                region2DepthName = region2DepthName,
                region3DepthName = region3DepthName,
                zoneNo = zoneNo
            )
        )
}
