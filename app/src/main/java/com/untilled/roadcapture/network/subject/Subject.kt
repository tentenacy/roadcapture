package com.untilled.roadcapture.network.subject

abstract class Subject<T>: ISubject<T> {

    protected val observers = hashSetOf<T>()
    protected var count = 0
    protected var refreshCount = 0

    override fun registerObserver(observer: T) {
        observers.add(observer)
    }

    override fun unregisterObserver(observer: T) {
        observers.remove(observer)
    }

    fun resetCount() {
        count = 0
    }

    fun resetRefreshCount() {
        refreshCount = 0
    }
}