package com.untilled.roadcapture.data.entity.token

import com.chibatching.kotpref.KotprefModel

object OAuthToken: KotprefModel() {
    var accessToken by stringPref()
    var refreshToken by nullableStringPref()
    var socialType by stringPref()
}