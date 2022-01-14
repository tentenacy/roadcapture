package com.untilled.roadcapture.data.datasource.dao

import com.untilled.roadcapture.data.datasource.api.dto.user.TokenResponse
import com.untilled.roadcapture.data.entity.token.NaverOAuthToken
import com.untilled.roadcapture.data.repository.token.dto.OAuthTokenArgs

interface LocalOAuthTokenDao {
    fun saveToken(args: OAuthTokenArgs)
    fun getToken(): TokenResponse
}