package com.untilled.roadcapture.features.root.studio

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.untilled.roadcapture.data.datasource.api.dto.user.UserResponse
import com.untilled.roadcapture.data.datasource.api.dto.user.UsersResponse
import com.untilled.roadcapture.data.entity.User
import com.untilled.roadcapture.data.repository.follow.FollowRepository
import com.untilled.roadcapture.data.repository.token.LocalTokenRepository
import com.untilled.roadcapture.data.repository.user.LocalUserRepository
import com.untilled.roadcapture.data.repository.user.UserRepository
import com.untilled.roadcapture.features.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class StudioViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val localTokenRepository: LocalTokenRepository,
    private val localUserRepository: LocalUserRepository,
    private val followRepository: FollowRepository
) : BaseViewModel() {

    private val _user = MutableLiveData<UsersResponse>()
    val user: LiveData<UsersResponse> get() = _user

    init {
        getUserInfo(localUserRepository.getUser())
    }

    fun getUserInfo(id: Int){
        userRepository.getUserInfo(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ userResponse ->
                _user.postValue(userResponse)
            },{ error ->
                Logger.d("test: $error")
            })
    }

    fun follow(id: Int){
        followRepository.follow(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            },{ error ->
                Logger.d("test: $error")
            })
    }

}

