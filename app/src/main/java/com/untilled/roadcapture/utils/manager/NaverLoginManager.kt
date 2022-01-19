package com.untilled.roadcapture.utils.manager

import android.app.Activity
import android.app.Application
import com.nhn.android.naverlogin.OAuthLogin
import com.orhanobut.logger.Logger
import com.untilled.roadcapture.network.subject.OAuthLoginManagerSubject
import javax.inject.Inject

class NaverLoginManager @Inject constructor(
    private val application: Application,
    private val oauthLogin: OAuthLogin
) : OAuthLoginManagerSubject() {
    override fun logout() {
        oauthLogin.logout(application.applicationContext)
        Logger.d("logout")
    }

    override fun withdrawal() {
    }

    override fun validateToken() {
    }
}