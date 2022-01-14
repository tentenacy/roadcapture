package com.untilled.roadcapture.data.api.dto.common

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Sort(
    val empty: Boolean,
    val sorted: Boolean,
    val unsorted: Boolean
) : Parcelable