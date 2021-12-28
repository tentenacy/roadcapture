package com.untilled.roadcapture.data.response.albums

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pageable(
    val offset: Int,
    val pageNumber: Int,
    val pageSize: Int,
    val paged: Boolean,
    val sort: Sort,
    val unpaged: Boolean
) : Parcelable