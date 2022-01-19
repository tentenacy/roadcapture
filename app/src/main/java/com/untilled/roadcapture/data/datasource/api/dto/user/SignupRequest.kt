package com.untilled.roadcapture.data.datasource.api.dto.user

data class SignupRequest(
    var email: String = "",
    var password: String = "",
    var username: String = "",
)