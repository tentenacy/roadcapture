package com.untilled.roadcapture.data.entity.token

import com.chibatching.kotpref.KotprefModel

object FacebookOAuthToken: KotprefModel() {
    var accessToken by stringPref()
    var expiresIn by intPref()
    var refreshToken by nullableStringPref()
    var tokenType by nullableStringPref()
}