package com.untilled.roadcapture.data.datasource.api.dto.user

data class TokenResponse(
    val grantType: String,
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExpireDate: Int,
)