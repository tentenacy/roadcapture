package com.untilled.roadcapture.data.datasource.dao

import com.untilled.roadcapture.data.datasource.api.dto.user.TokenResponse
import com.untilled.roadcapture.data.entity.token.KakaoOAuthToken
import com.untilled.roadcapture.data.entity.token.NaverOAuthToken
import com.untilled.roadcapture.data.repository.token.dto.OAuthTokenArgs
import javax.inject.Inject

class KakaoOAuthTokenDao @Inject constructor(): LocalOAuthTokenDao {

    override fun saveToken(args: OAuthTokenArgs) {
        KakaoOAuthToken.accessToken = args.accessToken
        KakaoOAuthToken.refreshToken = args.refreshToken
        KakaoOAuthToken.expiresIn = args.expiresIn
        KakaoOAuthToken.tokenType = args.tokenType
    }

    override fun getToken(): TokenResponse {
        return TokenResponse(
            grantType = KakaoOAuthToken.tokenType ?: "",
            accessToken = KakaoOAuthToken.accessToken,
            refreshToken = KakaoOAuthToken.refreshToken ?: "",
            accessTokenExpireDate = KakaoOAuthToken.expiresIn,
        )
    }
}