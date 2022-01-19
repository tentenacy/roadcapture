package com.untilled.roadcapture.data.datasource.api.dto.user

data class LoginRequest(
    var email: String = "",
    var password: String = "",
)