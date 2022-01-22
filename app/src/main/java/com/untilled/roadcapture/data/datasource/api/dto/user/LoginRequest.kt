package com.untilled.roadcapture.data.datasource.api.dto.user

data class LoginRequest(
    val email: String,
    val password: String,
)