package com.untilled.roadcapture.features.signup

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class SignupViewModel() : ViewModel() {

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val passwordVerification = MutableLiveData<String>()
    val username = MutableLiveData<String>()

    init{
        email.value = ""
        password.value = ""
        passwordVerification.value = ""
        username.value = ""
    }

}