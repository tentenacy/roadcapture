package com.untilled.roadcapture.data.repository.token

import com.untilled.roadcapture.data.repository.token.dto.NaverOAuthTokenArgs
import com.untilled.roadcapture.data.repository.token.dto.TokenArgs
import com.untilled.roadcapture.utils.type.SocialType

interface TokenRepository {
    fun saveKakaoOAuthToken(args: NaverOAuthTokenArgs)
    fun saveGoogleOAuthToken(args: NaverOAuthTokenArgs)
    fun saveNaverOAuthToken(args: NaverOAuthTokenArgs)
    fun saveFacebookOAuthToken(args: NaverOAuthTokenArgs)
    fun saveToken(tokenArgs: TokenArgs)
}