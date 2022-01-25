package com.untilled.roadcapture.utils.manager

import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.orhanobut.logger.Logger
import com.untilled.roadcapture.network.subject.OAuthLoginManagerSubject
import javax.inject.Inject

class GoogleLoginManager @Inject constructor(private val googleSignInClient: GoogleSignInClient) :
    OAuthLoginManagerSubject() {
    override fun logout() {
        googleSignInClient.signOut()
        Logger.d("logout")
    }

    override fun withdrawal() {
    }
}