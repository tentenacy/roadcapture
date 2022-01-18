package com.untilled.roadcapture.utils.manager

import com.facebook.login.LoginManager
import com.orhanobut.logger.Logger
import javax.inject.Inject

class FacebookLoginManager @Inject constructor(private val loginManager: LoginManager): OAuthLoginManager {
    override fun logout() {
        loginManager.logOut()
        Logger.d("logout")
    }

    override fun reissue() {
    }

    override fun withdrawal() {
    }
}