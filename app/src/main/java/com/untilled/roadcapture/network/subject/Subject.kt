package com.untilled.roadcapture.network.subject

abstract class Subject<T>: ISubject<T> {

    protected val observers = hashSetOf<T>()

    override fun registerObserver(observer: T) {
        observers.add(observer)
    }

    override fun unregisterObserver(observer: T) {
        observers.remove(observer)
    }
}