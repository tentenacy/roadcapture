package com.untilled.roadcapture.data.dto.common

import android.os.Parcelable
import com.untilled.roadcapture.data.dto.common.Sort
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