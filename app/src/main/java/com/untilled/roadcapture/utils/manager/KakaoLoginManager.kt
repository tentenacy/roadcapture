package com.untilled.roadcapture.utils.manager

import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.user.UserApiClient
import com.orhanobut.logger.Logger
import com.untilled.roadcapture.network.observer.OAuthTokenExpirationObserver
import com.untilled.roadcapture.network.subject.OAuthLoginManagerSubject
import javax.inject.Inject

class KakaoLoginManager @Inject constructor(
    private val userApiClient: UserApiClient,
    private val authApiClient: AuthApiClient,
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

    override fun validateToken() {
        if(authApiClient.hasToken()) {
            userApiClient.accessTokenInfo { _, error ->
                if (error != null) {
                    if (error is KakaoSdkError && error.isInvalidTokenError()) {
                        //로그인 필요
                        notifyOAuthRefreshTokenExpired()
                    } else {
                        Logger.e("error: ${error.message}")
                    }
                }/* else {
                    //토큰 유효성 체크 성공(필요 시 토큰 갱신됨)
                }*/
            }
        } else {
            //로그인 필요
            if(++count == 1) {
                notifyOAuthRefreshTokenExpired()
            }
        }
    }

    private fun notifyOAuthRefreshTokenExpired() {
        observers.forEach(OAuthTokenExpirationObserver::onOAuthTokenExpired)
    }

    override fun withdrawal() {
    }
}