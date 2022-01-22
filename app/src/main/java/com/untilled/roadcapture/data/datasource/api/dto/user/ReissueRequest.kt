package com.untilled.roadcapture.data.datasource.api.dto.user

data class ReissueRequest(
    val accessToken: String,
    val refreshToken: String,
)
