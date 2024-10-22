package com.untilled.roadcapture.data.datasource.sharedpref

import com.chibatching.kotpref.KotprefModel

object OAuthToken: KotprefModel() {
    var accessToken by stringPref()
    var refreshToken by nullableStringPref()
    var socialType by stringPref()
}