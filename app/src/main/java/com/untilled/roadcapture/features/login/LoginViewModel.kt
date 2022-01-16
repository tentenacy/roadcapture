package com.untilled.roadcapture.features.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.untilled.roadcapture.data.datasource.api.dto.user.ReissueRequest
import com.untilled.roadcapture.data.repository.token.LocalTokenRepository
import com.untilled.roadcapture.data.repository.token.dto.OAuthTokenArgs
import com.untilled.roadcapture.data.repository.user.UserRepository
import com.untilled.roadcapture.features.base.BaseViewModel
import com.untilled.roadcapture.network.interceptor.AuthenticationInterceptor
import com.untilled.roadcapture.network.subject.TokenExpirationObserver
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
) : BaseViewModel() {

    private var _isLoggingIn = MutableLiveData(false)
    val isLoggingIn: LiveData<Boolean> get() = _isLoggingIn

    private var _isLoggedIn = MutableLiveData(false)
    val isLoggedIn: LiveData<Boolean> get() = _isLoggedIn

    init {
        initData()
    }

    private fun initData() {
        _isLoggedIn.postValue(
            localTokenRepository.getToken().hasToken() or localTokenRepository.getOAuthToken().hasToken()
                .apply {
                    if(this) {
                        localTokenRepository.getOAuthToken().socialType.getSocialType()?.let { socialLogin(it) }
                    }
                }
        )
    }

    fun saveOAuthToken(args: OAuthTokenArgs) {
        localTokenRepository.saveOAuthToken(args)
    }

    fun socialLogin(socialType: SocialType) {
        userRepository.socialSignup(socialType)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                isLoading.addSource(_isLoggingIn.apply { value = true }) {
                    isLoading.value = it
                }
            }
            .doOnTerminate {
                isLoading.removeSource(_isLoggingIn.apply { value = false })
            }
            .subscribe({ response ->

            }) { t ->
                error.value = t.message
            }.addTo(compositeDisposable)
    }
}