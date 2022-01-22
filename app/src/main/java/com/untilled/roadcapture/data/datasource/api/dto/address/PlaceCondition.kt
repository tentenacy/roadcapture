package com.untilled.roadcapture.data.datasource.api.dto.address

import com.google.gson.annotations.SerializedName

data class PlaceCondition(
    @SerializedName("placeCond.region1DepthName") val address1: String? = null,
    @SerializedName("placeCond.region2DepthName") val address2: String? = null,
    @SerializedName("placeCond.region3DepthName") val address3: String? = null
)
