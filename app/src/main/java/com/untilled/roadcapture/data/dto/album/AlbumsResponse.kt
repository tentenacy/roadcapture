package com.untilled.roadcapture.data.dto.album

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.untilled.roadcapture.data.dto.common.Pageable
import com.untilled.roadcapture.data.dto.common.Sort
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AlbumsResponse(
    @SerializedName("content")
    val albums: List<Albums>,
    val pageable: Pageable,
    val totalElements: Int,
    val totalPages: Int,
    val last: Boolean,
    val size: Int,
    val number: Int,
    val sort: Sort,
    val numberOfElements: Int,
    val first: Boolean,
    val empty: Boolean
) : Parcelable