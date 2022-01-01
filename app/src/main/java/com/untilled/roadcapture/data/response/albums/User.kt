package com.untilled.roadcapture.data.response.albums

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var id: Int,
    var profileImageUrl: String,
    var username: String
) : Parcelable