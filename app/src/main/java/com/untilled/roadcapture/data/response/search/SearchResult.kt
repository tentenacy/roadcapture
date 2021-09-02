package com.untilled.roadcapture.data.response.search

import com.google.gson.annotations.SerializedName

data class SearchResult(
    @SerializedName("name")
    var name: String,
    @SerializedName("address_number")
    var addressNumber: String,
    @SerializedName("roadname")
    var roadname: String
)