package com.untilled.roadcapture.data.datasource.dao

import com.untilled.roadcapture.data.datasource.api.dto.user.TokenResponse
import com.untilled.roadcapture.data.entity.token.GoogleOAuthToken
import com.untilled.roadcapture.data.entity.token.KakaoOAuthToken
import com.untilled.roadcapture.data.entity.token.NaverOAuthToken
import com.untilled.roadcapture.data.repository.token.dto.OAuthTokenArgs
import javax.inject.Inject

class GoogleOAuthTokenDao @Inject constructor(): LocalOAuthTokenDao {

    override fun saveToken(args: OAuthTokenArgs) {
        GoogleOAuthToken.accessToken = args.accessToken
        GoogleOAuthToken.refreshToken = args.refreshToken
        GoogleOAuthToken.expiresIn = args.expiresIn
        GoogleOAuthToken.tokenType = args.tokenType
    }

    override fun getToken(): TokenResponse {
        return TokenResponse(
            grantType = GoogleOAuthToken.tokenType ?: "",
            accessToken = GoogleOAuthToken.accessToken,
            refreshToken = GoogleOAuthToken.refreshToken ?: "",
            accessTokenExpireDate = GoogleOAuthToken.expiresIn,
        )
    }
}