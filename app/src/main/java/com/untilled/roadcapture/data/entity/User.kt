package com.untilled.roadcapture.data.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class User(
    val id: Long = 0,
    val email: String = " ",
    var username: String = "",
    var profileImageUrl: String? = " ",
    var introduction: String? = "",
    val provider: String? = null,
    val address: String? = null,
    @SerializedName("background_url")
    var backgroundUrl: String? = "",
    @SerializedName("update")
    var update: Int = 0
): Parcelable
