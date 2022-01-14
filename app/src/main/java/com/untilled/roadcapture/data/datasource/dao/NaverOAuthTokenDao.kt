package com.untilled.roadcapture.data.datasource.dao

import com.untilled.roadcapture.data.datasource.api.dto.user.TokenResponse
import com.untilled.roadcapture.data.entity.token.NaverOAuthToken
import com.untilled.roadcapture.data.repository.token.dto.OAuthTokenArgs
import javax.inject.Inject

class NaverOAuthTokenDao @Inject constructor(): LocalOAuthTokenDao {

    override fun saveToken(args: OAuthTokenArgs) {
        NaverOAuthToken.accessToken = args.accessToken
        NaverOAuthToken.refreshToken = args.refreshToken
        NaverOAuthToken.expiresIn = args.expiresIn
        NaverOAuthToken.tokenType = args.tokenType
    }

    override fun getToken(): TokenResponse {
        return TokenResponse(
            grantType = NaverOAuthToken.tokenType ?: "",
            accessToken = NaverOAuthToken.accessToken,
            refreshToken = NaverOAuthToken.refreshToken ?: "",
            accessTokenExpireDate = NaverOAuthToken.expiresIn,
        )
    }
}