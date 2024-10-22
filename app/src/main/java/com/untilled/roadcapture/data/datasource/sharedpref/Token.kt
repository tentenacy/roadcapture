package com.untilled.roadcapture.data.datasource.sharedpref

import com.chibatching.kotpref.KotprefModel

object Token: KotprefModel() {
    var grantType by stringPref()
    var accessToken by stringPref()
    var refreshToken by stringPref()
    var accessTokenExpireDate by longPref()
}