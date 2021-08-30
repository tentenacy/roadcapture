package com.untilled.roadcapture.data.entity

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("username")
    var username: String,
    @SerializedName("profile_url")
    var profileUrl: String
)
