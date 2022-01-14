package com.untilled.roadcapture.features.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.untilled.roadcapture.data.entity.token.Token
import com.untilled.roadcapture.data.repository.token.TokenRepository
import com.untilled.roadcapture.data.repository.token.dto.NaverOAuthTokenArgs
import com.untilled.roadcapture.data.repository.token.dto.TokenArgs
import com.untilled.roadcapture.data.repository.user.UserRepository
import com.untilled.roadcapture.features.base.BaseViewModel
import com.untilled.roadcapture.utils.type.SocialType
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepository: UserRepository, private val tokenRepository: TokenRepository): BaseViewModel() {

    private var _isLoginComplete = MutableLiveData<Boolean>(false)
    val isLoginComplete: LiveData<Boolean> get() = _isLoginComplete

    init {
        isLoading.addSource(_isLoginComplete) {
            isLoading.value = it
        }
    }

    fun saveNaverOAuthToken(args: NaverOAuthTokenArgs) {
        tokenRepository.saveNaverOAuthToken(args)
    }

    fun socialLogin(socialType: SocialType) {
        userRepository.socialSignup(socialType)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                _isLoginComplete.value = false
            }
            .doOnTerminate {
                _isLoginComplete.value = true
            }
            .subscribe({ response ->
                tokenRepository.saveToken(TokenArgs(
                    grantType = response.grantType,
                    accessToken = response.accessToken,
                    refreshToken = response.refreshToken,
                    accessTokenExpireDate = response.accessTokenExpireDate.toLong(),
                ))
            }) { t ->
                error.value = t.message
            }.addTo(compositeDisposable)
    }
}