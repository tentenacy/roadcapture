package com.untilled.roadcapture.data.datasource.dao

import com.untilled.roadcapture.data.repository.token.dto.OAuthTokenArgs
import com.untilled.roadcapture.utils.type.SocialType

interface LocalOAuthTokenDao {
    fun saveToken(args: OAuthTokenArgs)
    fun getToken(): OAuthTokenArgs
}