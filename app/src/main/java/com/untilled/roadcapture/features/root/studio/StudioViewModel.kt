package com.untilled.roadcapture.features.root.studio

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.untilled.roadcapture.data.entity.User
import com.untilled.roadcapture.data.repository.token.LocalTokenRepository
import com.untilled.roadcapture.data.repository.user.UserRepository
import com.untilled.roadcapture.features.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class StudioViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val localTokenRepository: LocalTokenRepository
) : BaseViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    init {
        getUserDetail(localTokenRepository.getToken().accessToken)
    }

    private fun getUserDetail(token: String) {
        userRepository.getUserDetail(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ user ->
                _user.postValue(user)
            }, { error ->
                Logger.d("test: $error")
            })
    }

}
