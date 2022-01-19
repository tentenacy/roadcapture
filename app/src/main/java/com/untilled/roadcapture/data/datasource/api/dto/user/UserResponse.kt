package com.untilled.roadcapture.data.datasource.api.dto.user

import com.untilled.roadcapture.data.datasource.api.dto.address.Address

data class UserResponse(
    val id: Int,
    val email: String,
    var username: String,
    var profileImageUrl: String?,
    var introduction: String?,
    val provider: String?,
    val address: Address?
)
