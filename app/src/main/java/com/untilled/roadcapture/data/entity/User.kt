package com.untilled.roadcapture.data.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class User(
    @SerializedName("username")
    var username: String = "",
    @SerializedName("profile_url")
    var profileUrl: String = "",
    @SerializedName("background_url")
    var backgroundUrl: String = "",
    @SerializedName("description")
    var description: String = "",
    @SerializedName("update")
    var update: Int = 0
): Parcelable
