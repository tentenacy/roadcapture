package com.untilled.roadcapture.application

import androidx.lifecycle.ViewModel
import com.untilled.roadcapture.data.datasource.api.dto.user.ReissueRequest
import com.untilled.roadcapture.data.repository.token.LocalTokenRepository
import com.untilled.roadcapture.data.repository.user.UserRepository
import com.untilled.roadcapture.features.base.BaseViewModel
import com.untilled.roadcapture.network.interceptor.AuthenticationInterceptor
import com.untilled.roadcapture.network.subject.TokenExpirationObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val localTokenRepository: LocalTokenRepository,
    private val tokenExpirationObservable: AuthenticationInterceptor,
): BaseViewModel(), TokenExpirationObserver {

    init {
        tokenExpirationObservable.registerObserver(this)
    }

    override fun onCleared() {
        super.onCleared()
        tokenExpirationObservable.unregisterObserver(this)
    }

    override fun onTokenExpired() {
        val token = localTokenRepository.getToken()
        userRepository.reissue(ReissueRequest(token.accessToken, token.refreshToken))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->

            }) { t ->
                error.postValue(t.message)
            }.addTo(compositeDisposable)
    }

    fun logout() {

        localTokenRepository.clearToken()
    }
}