package com.untilled.roadcapture.data.datasource.api.dto.address

import com.google.gson.annotations.SerializedName

data class TmapAddressInfoResponse(
    @SerializedName("addressInfo")
    val tmapAddressInfo: TmapAddressInfo
)
