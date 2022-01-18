package com.untilled.roadcapture.data.datasource.api.dto.user

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UsersResponse(
    var id: Int,
    var profileImageUrl: String?,
    var username: String,
    var introduction: String?
) : Parcelable