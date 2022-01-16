package com.untilled.roadcapture.network.subject

interface Subject<T> {
    fun registerObserver(observer: T)
    fun unregisterObserver(observer: T)
}