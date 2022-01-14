package com.untilled.roadcapture.data.repository.token.dto

data class OAuthTokenArgs(
    var accessToken: String,
    var expiresIn: Int,
    var refreshToken: String,
    var tokenType: String,
)