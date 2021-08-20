package com.untilled.roadcapture.data.entity

import com.google.gson.annotations.SerializedName

data class Studio (
    @SerializedName("created_date")
    var createdDate: String,
    @SerializedName("title")
    var title: String,
    @SerializedName("description")
    var description: String,
    @SerializedName("thumbnail_url")
    var thumbnailUrl: String
)