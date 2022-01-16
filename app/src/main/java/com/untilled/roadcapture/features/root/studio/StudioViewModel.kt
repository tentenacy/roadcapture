package com.untilled.roadcapture.features.root.studio

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.untilled.roadcapture.data.datasource.api.dto.user.Users
import com.untilled.roadcapture.data.entity.User
import com.untilled.roadcapture.data.repository.follow.FollowRepository
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
    private val localTokenRepository: LocalTokenRepository,
    private val followRepository: FollowRepository
) : BaseViewModel() {

    private val _myUser = MutableLiveData<User>()
    private val _user = MutableLiveData<Users>()
    val myUser: LiveData<User> get() = _myUser
    val user: LiveData<Users> get() = _user

    init {
        getUserDetail(localTokenRepository.getToken().accessToken)
    }


    private fun getUserDetail(token: String){
        userRepository.getUserDetail(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ user ->
                _myUser.postValue(user)
            },{ error ->
                Logger.d("test: $error")
            })
    }

    fun getUserInfo(id: Int, token: String){
        userRepository.getUserInfo(id,token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ userResponse ->
                _user.postValue(userResponse)
            },{ error ->
                Logger.d("test: $error")
            })
    }

    fun follow(id: Int, token: String){
        followRepository.follow(id,token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            },{ error ->
                Logger.d("test: $error")
            })
    }

}

