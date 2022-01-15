package com.untilled.roadcapture.data.datasource.dao

import com.untilled.roadcapture.data.datasource.dao.dto.OAuthTokenDto
import com.untilled.roadcapture.data.entity.token.OAuthToken
import com.untilled.roadcapture.data.repository.token.dto.OAuthTokenArgs
import javax.inject.Inject

class KotPrefOAuthTokenDao @Inject constructor(): LocalOAuthTokenDao {

    override fun saveToken(args: OAuthTokenArgs) {
        OAuthToken.accessToken = args.accessToken
        OAuthToken.refreshToken = args.refreshToken
        OAuthToken.expiresIn = args.expiresIn
        OAuthToken.tokenType = args.tokenType
    }

    override fun getToken(): OAuthTokenDto {
        return OAuthTokenDto(
            tokenType = OAuthToken.tokenType,
            accessToken = OAuthToken.accessToken,
            refreshToken = OAuthToken.refreshToken,
            expiresIn = OAuthToken.expiresIn,
        )
    }
}