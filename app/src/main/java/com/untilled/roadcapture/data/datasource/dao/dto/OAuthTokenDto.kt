package com.untilled.roadcapture.data.datasource.dao.dto

data class OAuthTokenDto(
    var accessToken: String,
    var refreshToken: String?,
)