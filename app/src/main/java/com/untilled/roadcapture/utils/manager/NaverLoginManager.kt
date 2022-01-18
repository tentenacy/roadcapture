package com.untilled.roadcapture.utils.manager

import android.app.Activity
import com.nhn.android.naverlogin.OAuthLogin
import com.orhanobut.logger.Logger
import javax.inject.Inject

class NaverLoginManager @Inject constructor(private val activity: Activity, private val oauthLogin: OAuthLogin): OAuthLoginManager {
    override fun logout() {
        oauthLogin.logout(activity)
        Logger.d("logout")
    }

    override fun reissue() {
    }

    override fun withdrawal() {
    }
}