package com.untilled.roadcapture.data.repository.token.dto

import com.untilled.roadcapture.data.entity.token.NaverOAuthToken

data class NaverOAuthTokenArgs(
    var accessToken: String,
    var expiresIn: Int,
    var refreshToken: String,
    var tokenType: String,
)