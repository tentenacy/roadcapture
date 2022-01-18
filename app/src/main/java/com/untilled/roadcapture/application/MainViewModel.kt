package com.untilled.roadcapture.application

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.untilled.roadcapture.data.datasource.api.dto.user.ReissueRequest
import com.untilled.roadcapture.data.repository.token.LocalTokenRepository
import com.untilled.roadcapture.data.repository.token.dto.TokenArgs
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

    private var _originToLoginFragment = MutableLiveData<ConstraintLayout>()
    val originToLoginFragment: LiveData<ConstraintLayout> = _originToLoginFragment

    init {
        tokenExpirationObservable.registerObserver(this)
    }

    override fun onCleared() {
        super.onCleared()
        tokenExpirationObservable.unregisterObserver(this)
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

    fun logout(bindingRoot: ConstraintLayout) {

        _originToLoginFragment.value = bindingRoot

        localTokenRepository.clearToken()
    }
}