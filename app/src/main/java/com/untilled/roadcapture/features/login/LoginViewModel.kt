package com.untilled.roadcapture.features.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.untilled.roadcapture.data.repository.token.LocalTokenRepository
import com.untilled.roadcapture.data.repository.token.dto.OAuthTokenArgs
import com.untilled.roadcapture.data.repository.token.dto.TokenArgs
import com.untilled.roadcapture.data.repository.user.UserRepository
import com.untilled.roadcapture.features.base.BaseViewModel
import com.untilled.roadcapture.utils.manager.OAuthLoginManager
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
) : BaseViewModel() {

    private var _isLoggingIn = MutableLiveData(true)
    val isLoggingIn: LiveData<Boolean> get() = _isLoggingIn

    fun autoLogin() {
        localTokenRepository.getOAuthToken().whenHasOAuthTokenOrNot ({
            socialLogin(it)
        }, {
//            login()
        })
    }

    fun saveOAuthToken(args: OAuthTokenArgs) {
        localTokenRepository.saveOAuthToken(args)
    }

    fun socialLogin(socialType: SocialType) {
        userRepository.socialSignup(socialType)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                isLoading.addSource(_isLoggingIn) {
                    isLoading.value = it
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
                isLoading.removeSource(_isLoggingIn.apply { value = false })
            }) { t ->
                isLoading.removeSource(_isLoggingIn)
                localTokenRepository.clearToken()
                error.value = t.message
            }.addTo(compositeDisposable)
    }
}