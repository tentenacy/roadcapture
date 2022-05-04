package com.untilled.roadcapture.features.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.untilled.roadcapture.data.datasource.api.dto.user.LoginRequest
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
class LoginEmailViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val localTokenRepository: LocalTokenRepository,
    private val localUserRepository: LocalUserRepository,
): BaseViewModel() {

    private var _isLoggedIn = MutableLiveData<Boolean>()
    val isLoggedIn: LiveData<Boolean> get() = _isLoggedIn

    fun login(loginRequest: LoginRequest) {
        userRepository.login(loginRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                loading.addSource(_isLoggedIn.apply { value = false }) {
                    loading.value = !it
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
                error.value = t.message
            }
            .addTo(compositeDisposable)
    }

    private fun saveUserId() {
        userRepository.getUserDetail()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                localUserRepository.saveUser(response.id)
                loading.removeSource(_isLoggedIn.apply { value = true })
            }) { t ->
                logout()
                loading.removeSource(_isLoggedIn)
                loading.value = false
                error.value = t.message
            }.addTo(compositeDisposable)
    }

    private fun logout() {
        localUserRepository.clearUser()
        localTokenRepository.clearToken()
    }
}