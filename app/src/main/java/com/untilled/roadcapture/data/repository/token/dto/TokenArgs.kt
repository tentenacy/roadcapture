package com.untilled.roadcapture.data.repository.token.dto

import com.untilled.roadcapture.utils.getSocialType
import com.untilled.roadcapture.utils.type.SocialType

data class TokenArgs(
    var grantType: String,
    var accessToken: String,
    var refreshToken: String,
    var accessTokenExpireDate: Long,
) {
    fun whenHasAccessToken(callback: () -> Unit): Boolean =
        if(accessToken.isNotBlank()) {
            callback()
            true
        } else {
            false
        }

    fun whenHasOAuthTokenOrNot(
        hasAccessTokenCallback: () -> Unit,
        hasNotAccessTokenCallback: () -> Unit
    ) {
        if (!whenHasAccessToken(hasAccessTokenCallback)) {
            hasNotAccessTokenCallback()
        }
    }
}
