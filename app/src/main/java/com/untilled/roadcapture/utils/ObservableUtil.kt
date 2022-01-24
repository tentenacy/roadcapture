package com.untilled.roadcapture.utils

import com.untilled.roadcapture.utils.constant.policy.RETRY_PREDICATE
import com.untilled.roadcapture.utils.constant.policy.RetryPolicyConstant
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleTransformer
import io.reactivex.rxjava3.kotlin.Flowables
import java.lang.Exception
import java.util.concurrent.TimeUnit

fun <T> Single<T>.retryThreeTimes(): Single<T> = retryWhen { attempts ->
    Flowables.zip(
        attempts.map { error -> if (error is Exception) error else throw error },
        Flowable.interval(1, TimeUnit.SECONDS)
    ).map { (error, retryCount) -> if (retryCount >= 3) throw error }
}

fun <T> applyRetryPolicy(
    vararg predicates: RETRY_PREDICATE,
    maxRetries: Long = RetryPolicyConstant.MAX_RETRIES,
    interval: Long = RetryPolicyConstant.DEFAULT_INTERVAL,
    unit: TimeUnit = TimeUnit.SECONDS,
    errorReturn: (Throwable) -> T
) = SingleTransformer<T, T> { single ->
    single.retryWhen { attempts ->
        Flowables.zip(
            attempts.map { error -> if (predicates.count { it(error) } > 0) error else throw error },
            Flowable.interval(interval, unit)
        ).map { (error, retryCount) -> if (retryCount >= maxRetries) throw error }
    }
        .onErrorReturn(errorReturn)
}