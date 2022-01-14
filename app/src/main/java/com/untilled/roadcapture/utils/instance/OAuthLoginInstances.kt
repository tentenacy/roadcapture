package com.untilled.roadcapture.utils.instance

import com.nhn.android.naverlogin.OAuthLogin

object OAuthLoginInstances {
    val naverOAuthLoginInstance: OAuthLogin = OAuthLogin.getInstance()
}