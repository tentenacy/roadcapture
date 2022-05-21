package com.untilled.roadcapture.application

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.untilled.roadcapture.data.repository.token.LocalTokenRepository
import com.untilled.roadcapture.data.repository.token.dto.TokenArgs
import com.untilled.roadcapture.data.repository.user.LocalUserRepository
import com.untilled.roadcapture.data.repository.user.UserRepository
import com.untilled.roadcapture.features.base.BaseViewModel
import com.untilled.roadcapture.features.login.LoginViewModel
import com.untilled.roadcapture.network.interceptor.TokenInterceptor
import com.untilled.roadcapture.network.observer.TokenExpirationObserver
import com.untilled.roadcapture.network.subject.OAuthLoginManagerSubject
import com.untilled.roadcapture.utils.type.SocialType
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val localTokenRepository: LocalTokenRepository,
    private val localUserRepository: LocalUserRepository,
    private val tokenExpirationObservable: TokenInterceptor,
    private val oauthLoginManagerMap: Map<String, @JvmSuppressWildcards OAuthLoginManagerSubject>,
) : BaseViewModel(), TokenExpirationObserver {

    companion object {
        const val EVENT_LEAVE = 1000
        const val EVENT_REMAIN = 1001
        const val EVENT_NAVIGATE_TO_LOGIN = 1002
    }

    init {
        tokenExpirationObservable.registerObserver(this)
    }

    fun autoLogin() {
        localTokenRepository.getOAuthToken().whenHasOAuthTokenOrNot(this::socialLogin) {
            localTokenRepository.getToken().whenHasOAuthTokenOrNot({
                userRepository.getUserDetail()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response ->
                        localUserRepository.saveUser(response)
                        viewEvent(Pair(EVENT_LEAVE, Unit))
                    }) { t ->
                        Logger.e("${t}")
                        logout()
                        viewEvent(Pair(EVENT_REMAIN, Unit))
                    }
            }) {
                viewEvent(Pair(EVENT_REMAIN, Unit))
            }
        }
    }

    private fun socialLogin(socialType: SocialType) {
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
                localUserRepository.saveUser(response)
                viewEvent(Pair(EVENT_LEAVE, Unit))
            }) { t ->
                loadingEvent(false)
                Logger.e("${t}")
                logout()
                viewEvent(Pair(EVENT_REMAIN, Unit))
            }.addTo(compositeDisposable)
    }

    override fun onCleared() {
        unregisterObservers()
        super.onCleared()
    }

    override fun onTokenExpired() {
        userRepository.reissue()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                with(response) {
                    localTokenRepository.saveToken(
                        TokenArgs(
                            grantType = grantType,
                            accessToken = accessToken,
                            refreshToken = refreshToken,
                            accessTokenExpireDate = accessTokenExpireDate.toLong(),
                        )
                    )
                    tokenExpirationObservable.resetTokenErrorOccurred()
                }
            }) { t ->
                error.value = t.message
            }.addTo(compositeDisposable)
    }

    override fun onRefreshTokenExpired() {
        logout()
    }

    fun logout() {
        localTokenRepository.getOAuthToken().whenHasOAuthToken {
            oauthLoginManagerMap[it.name]?.logout()
        }
        localUserRepository.clearUser()
        localTokenRepository.clearToken()

        viewEvent(Pair(EVENT_NAVIGATE_TO_LOGIN, Unit))
    }

    private fun unregisterObservers() {
        tokenExpirationObservable.unregisterObserver(this)
    }
}