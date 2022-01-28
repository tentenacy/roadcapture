package com.untilled.roadcapture.data.datasource.api.ext.dto.address

import com.google.gson.annotations.SerializedName
import com.untilled.roadcapture.data.entity.Place
import com.untilled.roadcapture.utils.DEFAULT_LATITUDE
import com.untilled.roadcapture.utils.DEFAULT_LONGITUDE

data class TmapAddressInfo(
    val fullAddress: String?,           // 전체 주소, 구분자(콤마)로 구분되어 3가지 주소 모두 같이 옴
    @SerializedName("city_do")
    val cityDo: String?,                // 시/도
    @SerializedName("gu_gun")
    val guGun: String?,                 // 군/구
    @SerializedName("eup_myun")
    val eupMyun: String?,           // 읍/면
    val adminDong: String?,         // 행정동
    val legalDong: String?,         // 법정동(읍/면)
    val ri: String?,                // 리
    val bunji: String?,             // 번지
    val lat : String?,              // 위도
    val lon : String?,              // 경도
    val roadName: String?,          // 도로명
    val buildingName: String?,      // 건물명
) {
    fun getPlaceName(): String {
        return if (fullAddress != null) {
            fullAddress.split(",")[0]
        } else {
            ""
        }
    }

    fun toPlace() :Place =
        Place(
            latitude = lat?.toDouble() ?: DEFAULT_LATITUDE,
            longitude = lon?.toDouble() ?: DEFAULT_LONGITUDE,
            name = getPlaceName(),       // 장소 이름
            addressName = getPlaceName(),        // 지번 주소
            roadAddressName = (cityDo?.trim() ?: "") + " " + (guGun?.trim() ?: "") + " " + (roadName?.trim() ?: "") + " " + (buildingName?.trim()) ,   // 도로명 주소
            region1DepthName = cityDo?: "",   // 시구명
            region2DepthName = guGun?: "",   // 시군구명
            region3DepthName = adminDong ?: legalDong ?: "",   // 읍면동명
        )
}
