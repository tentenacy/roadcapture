package com.untilled.roadcapture.application

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.untilled.roadcapture.data.repository.token.LocalTokenRepository
import com.untilled.roadcapture.data.repository.token.dto.TokenArgs
import com.untilled.roadcapture.data.repository.user.LocalUserRepository
import com.untilled.roadcapture.data.repository.user.UserRepository
import com.untilled.roadcapture.features.base.BaseViewModel
import com.untilled.roadcapture.network.interceptor.TokenInterceptor
import com.untilled.roadcapture.network.observer.OAuthRefreshTokenExpirationObserver
import com.untilled.roadcapture.network.observer.TokenExpirationObserver
import com.untilled.roadcapture.network.subject.OAuthLoginManagerSubject
import com.untilled.roadcapture.utils.manager.OAuthLoginManager
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
) : BaseViewModel(), TokenExpirationObserver, OAuthRefreshTokenExpirationObserver {

    private var _isLoggedOut = MutableLiveData<Boolean>(false)
    val isLoggedOut: LiveData<Boolean> get() = _isLoggedOut

    init {
        tokenExpirationObservable.registerObserver(this)
    }

    override fun onCleared() {
        tokenExpirationObservable.unregisterObserver(this)
        localTokenRepository.getOAuthToken().whenHasOAuthToken {
            oauthLoginManagerMap[it.name]?.unregisterObserver(this)
        }
        super.onCleared()
    }

    override fun onTokenExpired() {
        userRepository.reissue()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                localTokenRepository.saveToken(
                    TokenArgs(
                        grantType = response.grantType,
                        accessToken = response.accessToken,
                        refreshToken = response.refreshToken,
                        accessTokenExpireDate = response.accessTokenExpireDate.toLong(),
                    )
                )
            }) { t ->
                error.value = t.message
            }.addTo(compositeDisposable)
    }

    override fun onRefreshTokenExpired() {
        logout()
    }

    fun logout() {
        _isLoggedOut.value = true

        localTokenRepository.getOAuthToken().whenHasOAuthToken {
            oauthLoginManagerMap[it.name]?.logout()
        }
        localUserRepository.clearUser()
        localTokenRepository.clearToken()
    }

    fun registerToOAuthLoginManagerSubject(socialType: SocialType) {
        oauthLoginManagerMap[socialType.name]?.registerObserver(this)
    }
}