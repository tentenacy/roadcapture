package com.untilled.roadcapture.data.response.albums

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Sort(
    val empty: Boolean,
    val sorted: Boolean,
    val unsorted: Boolean
) : Parcelable