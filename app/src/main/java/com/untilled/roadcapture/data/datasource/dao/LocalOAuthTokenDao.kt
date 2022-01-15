package com.untilled.roadcapture.data.datasource.dao

import com.untilled.roadcapture.data.datasource.dao.dto.OAuthTokenDto
import com.untilled.roadcapture.data.repository.token.dto.OAuthTokenArgs

interface LocalOAuthTokenDao {
    fun saveToken(args: OAuthTokenArgs)
    fun getToken(): OAuthTokenDto
}