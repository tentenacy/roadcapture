package com.untilled.roadcapture.features.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private var _isLoggedIn = MutableLiveData<SocialType?>()
    val isLoggedIn: LiveData<SocialType?> get() = _isLoggedIn

    fun autoLogin() {
        localTokenRepository.getOAuthToken().whenHasOAuthTokenOrNot (this::socialLogin) {
            localTokenRepository.getToken().whenHasAccessToken {
                _isLoggedIn.value = null
            }
        }
    }

    fun saveOAuthToken(args: OAuthTokenArgs) {
        localTokenRepository.saveOAuthToken(args)
    }

    fun socialLogin(socialType: SocialType) {
        userRepository.socialSignup(socialType)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                loading.apply {
                    addSource(_isLoggedIn) {
                        loading.value = it?.name?.isBlank()
                    }
                    value = true
                }
            }
            .subscribe({ response ->
                localTokenRepository.saveToken(
                    TokenArgs(
                        grantType = response.grantType,
                        accessToken = response.accessToken,
                        refreshToken = response.refreshToken,
                        accessTokenExpireDate = response.accessTokenExpireDate.toLong(),
                    )
                )
                saveUserId()
            }) { t ->
                loading.removeSource(_isLoggedIn)
                loading.value = false
                localTokenRepository.clearToken()
                error.value = t.message
            }.addTo(compositeDisposable)
    }

    private fun saveUserId() {
        userRepository.getUserDetail()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                localUserRepository.saveUser(response.id)
                loading.removeSource(_isLoggedIn.apply { value = localTokenRepository.getOAuthToken().socialType.getSocialType() })
            }) { t->
                logout()
                loading.removeSource(_isLoggedIn)
                loading.value = false
                error.value = t.message
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