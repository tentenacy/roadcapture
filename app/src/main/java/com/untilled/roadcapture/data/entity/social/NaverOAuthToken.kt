package com.untilled.roadcapture.data.entity.social

import android.os.Parcelable
import com.chibatching.kotpref.KotprefModel
import kotlinx.android.parcel.Parcelize

object NaverOAuthToken: KotprefModel() {
    var accessToken by stringPref()
    var expiresIn by intPref()
    var refreshToken by nullableStringPref()
    var tokenType by nullableStringPref()
}