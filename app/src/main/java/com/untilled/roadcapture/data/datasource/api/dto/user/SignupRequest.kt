package com.untilled.roadcapture.data.datasource.api.dto.user

data class SignupRequest(
    val email: String,
    val password: String,
    val username: String,
)