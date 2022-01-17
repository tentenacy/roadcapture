package com.untilled.roadcapture.features.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.untilled.roadcapture.data.repository.token.LocalTokenRepository
import com.untilled.roadcapture.data.repository.token.dto.OAuthTokenArgs
import com.untilled.roadcapture.data.repository.user.UserRepository
import com.untilled.roadcapture.features.base.BaseViewModel
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

    init {
        initData()
    }

    private fun initData() {
        localTokenRepository.getOAuthToken().whenHasAccessToken {
            socialLogin(it)
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
                _isLoggingIn.value = true
                isLoading.addSource(_isLoggingIn) {
                    isLoading.value = it
                }
            }
            .subscribe({ response ->
                isLoading.removeSource(_isLoggingIn)
                _isLoggingIn.value = false
            }) { t ->
                isLoading.run {
                    removeSource(_isLoggingIn)
                    value = false
                }
                localTokenRepository.clearToken()
                error.value = t.message
            }.addTo(compositeDisposable)
    }
}