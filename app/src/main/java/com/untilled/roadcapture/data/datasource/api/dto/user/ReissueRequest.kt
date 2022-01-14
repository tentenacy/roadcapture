package com.untilled.roadcapture.data.datasource.api.dto.user

data class ReissueRequest(
    var accessToken: String,
    var refreshToken: String,
)
