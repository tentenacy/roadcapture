package com.untilled.roadcapture.utils

import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.Flowables
import java.io.IOException
import java.util.concurrent.TimeUnit

fun <T>Single<T>.retryThreeTimes(): Single<T> = retryWhen { attempts ->
    Flowables.zip(
        attempts.map { error -> if (error is IOException) error else throw error },
        Flowable.interval(1, TimeUnit.SECONDS)
    ).map { (error, retryCount) -> if (retryCount >= 3) throw error }
}