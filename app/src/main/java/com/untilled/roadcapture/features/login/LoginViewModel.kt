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
    private val localTokenRepository: LocalTokenRepository
) : BaseViewModel() {

    private var _isLoginComplete = MutableLiveData<Boolean>(false)
    val isLoginComplete: LiveData<Boolean> get() = _isLoginComplete

    init {
        isLoading.addSource(_isLoginComplete) {
            isLoading.value = it
        }
    }

    fun saveOAuthToken(socialType: SocialType, args: OAuthTokenArgs) {
        localTokenRepository.saveOAuthToken(socialType, args)
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
            }) { t ->
                error.value = t.message
            }.addTo(compositeDisposable)
    }
}