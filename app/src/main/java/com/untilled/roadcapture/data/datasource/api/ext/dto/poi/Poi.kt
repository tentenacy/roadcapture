package com.untilled.roadcapture.data.datasource.api.ext.dto.poi

import com.untilled.roadcapture.data.entity.Place

data class Poi(
    val name: String? = null,       //POI 의 name
    val frontLat: Float = 0.0f,     //시설물 입구 위도 좌표
    val frontLon: Float = 0.0f,     //시설물 입구 경도 좌표
    val noorLat: Float = 0.0f,      //중심점 위도 좌표
    val noorLon: Float = 0.0f,      //중심점 경도 좌표
    val upperAddrName: String? = null,  //표출 주소 대분류명
    val middleAddrName: String? = null, //표출 주소 중분류명
    val lowerAddrName: String? = null,  //표출 주소 소분류명
    val detailAddrName: String? = null, //표출 주소 세분류명
    val firstNo: String? = null,    //본번
    val secondNo: String? = null,   //부번
    val roadName: String? = null,   //도로명
    val firstBuildNo: String? = null,   //건물번호 1
    val secondBuildNo: String? = null   //건물번호 2
) {
    fun getAddressName(): String =
        if (secondNo?.trim().isNullOrEmpty()) {
            (upperAddrName?.trim() ?: "") + " " +
                    (middleAddrName?.trim() ?: "") + " " +
                    (lowerAddrName?.trim() ?: "") + " " +
                    (detailAddrName?.trim() ?: "") + " " +
                    firstNo?.trim()
        } else {
            (upperAddrName?.trim() ?: "") + " " +
                    (middleAddrName?.trim() ?: "") + " " +
                    (lowerAddrName?.trim() ?: "") + " " +
                    (detailAddrName?.trim() ?: "") + " " +
                    (firstNo?.trim() ?: "") + "-" +
                    secondNo?.trim()
        }

    fun getRoadAddressName(): String =
        if (secondBuildNo?.trim().isNullOrEmpty()) {
            (upperAddrName?.trim() ?: "") + " " +
                    (middleAddrName?.trim() ?: "") + " " +
                    (roadName?.trim() ?: "") + " " +
                    firstBuildNo?.trim()
        } else {
            (upperAddrName?.trim() ?: "") + " " +
                    (middleAddrName?.trim() ?: "") + " " +
                    (roadName?.trim() ?: "") + " " +
                    (firstBuildNo?.trim() ?: "") + "-" +
                    secondBuildNo?.trim()
        }

    fun toPlace(): Place =
        Place(
            latitude = noorLat.toDouble(),   // 위도
            longitude = noorLon.toDouble(),  // 경도
            name = name ?: "",       // 장소 이름
            addressName = getAddressName(),        // 지번 주소
            roadAddressName = getRoadAddressName(),   // 도로명 주소
            region1DepthName = upperAddrName ?: "",   // 시구명
            region2DepthName = middleAddrName ?: "",   // 시군구명
            region3DepthName = lowerAddrName ?: "",   // 읍면동명
        )
}
