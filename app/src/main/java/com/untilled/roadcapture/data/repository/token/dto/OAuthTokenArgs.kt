package com.untilled.roadcapture.data.repository.token.dto

import com.untilled.roadcapture.utils.getSocialType
import com.untilled.roadcapture.utils.type.SocialType

data class OAuthTokenArgs(
    var accessToken: String,
    var refreshToken: String?,
    var socialType: String,
) {

    fun whenHasOAuthTokenOrNot(
        hasAccessTokenCallback: (SocialType) -> Unit,
        hasNotAccessTokenCallback: () -> Unit
    ) {
        if (!whenHasOAuthToken(hasAccessTokenCallback)) {
            hasNotAccessTokenCallback()
        }
    }

    fun whenHasOAuthToken(hasAccessTokenCallback: (SocialType) -> Unit): Boolean =
        if (accessToken.isNotBlank()) {
            socialType.getSocialType()?.let { hasAccessTokenCallback(it) }
            true
        } else {
            false
        }
}