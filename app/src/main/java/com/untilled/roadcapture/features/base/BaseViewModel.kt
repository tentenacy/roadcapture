package com.untilled.roadcapture.features.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {

    protected val compositeDisposable = CompositeDisposable()

    val isLoading = MediatorLiveData<Boolean>()
    val error = MediatorLiveData<String>()

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}