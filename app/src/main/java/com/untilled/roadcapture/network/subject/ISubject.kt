package com.untilled.roadcapture.network.subject

interface ISubject<T> {
    fun registerObserver(observer: T)
    fun unregisterObserver(observer: T)
}