package com.untilled.roadcapture.utils.manager

import com.kakao.sdk.user.UserApiClient
import com.orhanobut.logger.Logger
import javax.inject.Inject

class KakaoLoginManager @Inject constructor(private val userApiClient: UserApiClient): OAuthLoginManager {
    override fun logout() {
        userApiClient.logout { error ->
            if(error == null) {
                Logger.d("logout successful")
            } else {
                Logger.e("error: ${error?.message}")
            }
        }
    }

    override fun reissue() {
    }

    override fun withdrawal() {
    }
}