package com.untilled.roadcapture.data.entity

import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("username")
    var username : String,

    @SerializedName("created_date")
    var createdDate : String,

    @SerializedName("content")
    var content : String,

    @SerializedName("profile_url")
    var profileUrl : String,

    )
