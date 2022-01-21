package com.untilled.roadcapture.data.repository.user

import com.untilled.roadcapture.data.datasource.api.dto.user.UserResponse
import com.untilled.roadcapture.data.repository.token.dto.OAuthTokenArgs
import com.untilled.roadcapture.data.repository.token.dto.TokenArgs

interface LocalUserRepository {
    fun saveUser(id: Long)
    fun getUser(): Long

    fun clearUser()
}