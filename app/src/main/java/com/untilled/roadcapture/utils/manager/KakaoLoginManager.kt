package com.untilled.roadcapture.utils.manager

import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.user.UserApiClient
import com.orhanobut.logger.Logger
import com.untilled.roadcapture.network.subject.OAuthLoginManagerSubject
import javax.inject.Inject

class KakaoLoginManager @Inject constructor(
    private val userApiClient: UserApiClient,
) :
    OAuthLoginManagerSubject() {
    override fun logout() {
        userApiClient.logout { error ->
            if (error == null) {
                Logger.d("logout successful")
            } else {
                Logger.e("error: ${error?.message}")
            }
        }
    }

    override fun withdrawal() {
    }
}