package com.untilled.roadcapture.features.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.untilled.roadcapture.data.datasource.api.dto.user.SignupRequest
import com.untilled.roadcapture.data.repository.token.LocalTokenRepository
import com.untilled.roadcapture.data.repository.token.dto.TokenArgs
import com.untilled.roadcapture.data.repository.user.LocalUserRepository
import com.untilled.roadcapture.data.repository.user.UserRepository
import com.untilled.roadcapture.features.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val localTokenRepository: LocalTokenRepository,
    private val localUserRepository: LocalUserRepository,
) : BaseViewModel() {

    companion object {
        const val EVENT_NAVIGATE_TO_ROOT = 1000
    }

    val email = MutableLiveData("")
    val password = MutableLiveData("")
    val passwordVerification = MutableLiveData("")
    val username = MutableLiveData("")

    fun signup() {
        loadingEvent(true)
        userRepository.signup(SignupRequest(
            email = email.value!!,
            password = password.value!!,
            username = username.value!!,
        ))
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
                viewEvent(Pair(EVENT_NAVIGATE_TO_ROOT, Unit))
            }) { t ->
                loadingEvent(false)
                logout()
                error.value = t.message
            }
            .addTo(compositeDisposable)
    }

    private fun logout() {
        localUserRepository.clearUser()
        localTokenRepository.clearToken()
    }
}