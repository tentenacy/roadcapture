package com.untilled.roadcapture.data.datasource.api.dto.user

import android.os.Parcelable
import com.untilled.roadcapture.data.datasource.api.dto.address.Address
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserResponse(
    val id: Long,
    val email: String,
    var username: String,
    var profileImageUrl: String?,
    var introduction: String?,
    val provider: String?,
    val address: Address?
): Parcelable
