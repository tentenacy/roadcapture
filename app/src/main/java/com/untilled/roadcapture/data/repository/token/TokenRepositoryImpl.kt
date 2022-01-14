package com.untilled.roadcapture.data.repository.token

import com.untilled.roadcapture.data.entity.token.NaverOAuthToken
import com.untilled.roadcapture.data.entity.token.Token
import com.untilled.roadcapture.data.repository.token.dto.NaverOAuthTokenArgs
import com.untilled.roadcapture.data.repository.token.dto.TokenArgs
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(): TokenRepository {
    override fun saveKakaoOAuthToken(args: NaverOAuthTokenArgs) {
    }

    override fun saveGoogleOAuthToken(args: NaverOAuthTokenArgs) {
    }

    override fun saveNaverOAuthToken(args: NaverOAuthTokenArgs) {
        NaverOAuthToken.accessToken = args.accessToken
        NaverOAuthToken.refreshToken = args.refreshToken
        NaverOAuthToken.expiresIn = args.expiresIn
        NaverOAuthToken.tokenType = args.tokenType
    }

    override fun saveFacebookOAuthToken(args: NaverOAuthTokenArgs) {
    }

    override fun saveToken(tokenArgs: TokenArgs) {
        Token.grantType = tokenArgs.grantType
        Token.accessToken = tokenArgs.accessToken
        Token.refreshToken = tokenArgs.refreshToken
        Token.accessTokenExpireDate = tokenArgs.accessTokenExpireDate
    }
}