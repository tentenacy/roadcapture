package com.untilled.roadcapture.data.api.dto.comment

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.untilled.roadcapture.data.api.dto.common.Pageable
import com.untilled.roadcapture.data.api.dto.common.Sort
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CommentsResponse(
    @SerializedName("content")
    val comments: List<Comments>,
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