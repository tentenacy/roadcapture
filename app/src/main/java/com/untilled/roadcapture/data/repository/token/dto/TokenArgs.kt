package com.untilled.roadcapture.data.repository.token.dto

data class TokenArgs(
    var grantType: String,
    var accessToken: String,
    var refreshToken: String,
    var accessTokenExpireDate: Long,
)
