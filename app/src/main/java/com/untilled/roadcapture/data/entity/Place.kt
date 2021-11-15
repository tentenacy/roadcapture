package com.untilled.roadcapture.data.entity

import com.google.gson.annotations.SerializedName

data class Place(
    @SerializedName("image")
    var image: String,

    @SerializedName("name")
    var name: String
)
