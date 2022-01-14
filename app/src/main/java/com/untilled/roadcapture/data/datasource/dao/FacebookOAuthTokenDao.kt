package com.untilled.roadcapture.data.datasource.dao

import com.untilled.roadcapture.data.datasource.api.dto.user.TokenResponse
import com.untilled.roadcapture.data.entity.token.FacebookOAuthToken
import com.untilled.roadcapture.data.entity.token.GoogleOAuthToken
import com.untilled.roadcapture.data.entity.token.NaverOAuthToken
import com.untilled.roadcapture.data.repository.token.dto.OAuthTokenArgs
import javax.inject.Inject

class FacebookOAuthTokenDao @Inject constructor(): LocalOAuthTokenDao {

    override fun saveToken(args: OAuthTokenArgs) {
        FacebookOAuthToken.accessToken = args.accessToken
        FacebookOAuthToken.refreshToken = args.refreshToken
        FacebookOAuthToken.expiresIn = args.expiresIn
        FacebookOAuthToken.tokenType = args.tokenType
    }

    override fun getToken(): TokenResponse {
        return TokenResponse(
            grantType = FacebookOAuthToken.tokenType ?: "",
            accessToken = FacebookOAuthToken.accessToken,
            refreshToken = FacebookOAuthToken.refreshToken ?: "",
            accessTokenExpireDate = FacebookOAuthToken.expiresIn,
        )
    }

}