package com.untilled.roadcapture.data.dto.user

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Users(
    var id: Int,
    var profileImageUrl: String,
    var username: String
) : Parcelable