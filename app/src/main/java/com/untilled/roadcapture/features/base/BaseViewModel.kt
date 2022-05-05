package com.untilled.roadcapture.features.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.untilled.roadcapture.utils.Event
import io.reactivex.rxjava3.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {

    protected val compositeDisposable = CompositeDisposable()

    val error = MediatorLiveData<String>()

    private val _viewEvent = MutableLiveData<Event<Pair<Int, Any>>>()
    val viewEvent: LiveData<Event<Pair<Int, Any>>>
        get() = _viewEvent

    private val _loadingEvent = MutableLiveData<Event<Boolean>>()
    val loadingEvent: LiveData<Event<Boolean>>
        get() = _loadingEvent

    protected fun viewEvent(content: Pair<Int, Any>) {
        _viewEvent.postValue(Event(content))
    }

    protected fun loadingEvent(content: Boolean) {
        _loadingEvent.postValue(Event(content))
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}