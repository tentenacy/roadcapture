package com.untilled.roadcapture.data.datasource.api.dto.picture

import android.os.Parcelable
import com.untilled.roadcapture.data.datasource.api.dto.place.PlaceResponse
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime

@Parcelize
data class PictureResponse(
    val id: Long,
    val createdAt: LocalDateTime,
    val lastModifiedAt: LocalDateTime,
    val imageUrl: String,
    val description: String = "",
    val place: PlaceResponse,
    val thumbnail: Boolean,
) : Parcelable