package com.untilled.roadcapture.features.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.untilled.roadcapture.data.datasource.sharedpref.User
import com.untilled.roadcapture.data.repository.token.LocalTokenRepository
import com.untilled.roadcapture.data.repository.token.dto.OAuthTokenArgs
import com.untilled.roadcapture.data.repository.token.dto.TokenArgs
import com.untilled.roadcapture.data.repository.user.LocalUserRepository
import com.untilled.roadcapture.data.repository.user.UserRepository
import com.untilled.roadcapture.features.base.BaseViewModel
import com.untilled.roadcapture.network.subject.OAuthLoginManagerSubject
import com.untilled.roadcapture.utils.getSocialType
import com.untilled.roadcapture.utils.type.SocialType
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val localTokenRepository: LocalTokenRepository,
    private val localUserRepository: LocalUserRepository,
    private val oauthLoginManagerMap: Map<String, @JvmSuppressWildcards OAuthLoginManagerSubject>,
) : BaseViewModel() {

    companion object {
        const val EVENT_NAVIGATE_TO_ROOT = 1000
    }

    fun autoLogin() {
        localTokenRepository.getOAuthToken().whenHasOAuthTokenOrNot (this::socialLogin) {
            localTokenRepository.getToken().whenHasAccessToken {
                loadingEvent(true)
                userRepository.getUserDetail()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response ->
                        loadingEvent(false)
                        localUserRepository.saveUser(response)
                        viewEvent(Pair(EVENT_NAVIGATE_TO_ROOT, Unit))
                    }) { t ->
                        loadingEvent(false)
                        Logger.e("${t}")
                    }
            }
        }
    }

    fun saveOAuthToken(args: OAuthTokenArgs) {
        localTokenRepository.saveOAuthToken(args)
    }

    fun socialLogin(socialType: SocialType) {
        loadingEvent(true)
        userRepository.socialSignup(socialType)
            .flatMap { response ->
                localTokenRepository.saveToken(
                    TokenArgs(
                        grantType = response.grantType,
                        accessToken = response.accessToken,
                        refreshToken = response.refreshToken,
                        accessTokenExpireDate = response.accessTokenExpireDate.toLong(),
                    )
                )
                userRepository.getUserDetail()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                loadingEvent(false)
                viewEvent(Pair(EVENT_NAVIGATE_TO_ROOT, Unit))
                localUserRepository.saveUser(response)
            }) { t ->
                loadingEvent(false)
                logout()
                Logger.e("${t}")
            }.addTo(compositeDisposable)
    }

    private fun logout() {
        localTokenRepository.getOAuthToken().whenHasOAuthToken {
            oauthLoginManagerMap[it.name]?.logout()
        }
        localUserRepository.clearUser()
        localTokenRepository.clearToken()
    }
}