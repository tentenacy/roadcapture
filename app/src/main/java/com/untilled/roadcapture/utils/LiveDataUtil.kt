package com.untilled.roadcapture.utils

import androidx.lifecycle.LiveData

fun <T, F>combineLatestData(
    first: LiveData<T>,
    second: LiveData<F>
): Pair<T, F>? {

    // Don't send a success until we have both results
    if (first.value == null || second.value == null) {
        return null
    }

    return Pair(first.value!!, second.value!!)
}

const val SUCCESS = 100
const val DEFAULT = 101
const val FAILURE = 102