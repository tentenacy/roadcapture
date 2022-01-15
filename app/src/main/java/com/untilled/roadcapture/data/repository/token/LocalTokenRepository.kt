package com.untilled.roadcapture.data.repository.token

import com.untilled.roadcapture.data.repository.token.dto.OAuthTokenArgs
import com.untilled.roadcapture.data.repository.token.dto.TokenArgs
import com.untilled.roadcapture.utils.type.SocialType

interface LocalTokenRepository {
    fun saveOAuthToken(args: OAuthTokenArgs)
    fun saveToken(args: TokenArgs)
}