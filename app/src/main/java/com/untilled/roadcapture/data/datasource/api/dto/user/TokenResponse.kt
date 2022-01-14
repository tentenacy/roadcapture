package com.untilled.roadcapture.data.datasource.api.dto.user

data class TokenResponse(
    var grantType: String,
    var accessToken: String,
    var refreshToken: String,
    var accessTokenExpireDate: Int,
)