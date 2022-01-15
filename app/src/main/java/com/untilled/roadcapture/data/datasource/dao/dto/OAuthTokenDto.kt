package com.untilled.roadcapture.data.datasource.dao.dto

data class OAuthTokenDto(
    var accessToken: String,
    var expiresIn: Int,
    var refreshToken: String?,
    var tokenType: String?,
)