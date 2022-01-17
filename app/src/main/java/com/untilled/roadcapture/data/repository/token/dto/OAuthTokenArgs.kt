package com.untilled.roadcapture.data.repository.token.dto

import com.untilled.roadcapture.utils.getSocialType
import com.untilled.roadcapture.utils.type.SocialType

data class OAuthTokenArgs(
    var accessToken: String,
    var refreshToken: String?,
    var socialType: String,
) {
    fun whenHasAccessToken(callback: (SocialType) -> Unit) {
        if(accessToken.isNotBlank()) {
            socialType.getSocialType()?.let { callback(it) }
        }
    }

}