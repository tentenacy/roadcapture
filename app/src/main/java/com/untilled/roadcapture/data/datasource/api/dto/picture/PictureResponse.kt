package com.untilled.roadcapture.data.datasource.api.dto.picture

import android.os.Parcelable
import com.untilled.roadcapture.data.datasource.api.dto.place.PlaceResponse
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime

@Parcelize
data class PictureResponse(
    val id: Long,
    var createdAt: LocalDateTime?,
    var lastModifiedAt: LocalDateTime?,
    var imageUrl: String,
    var description: String?,
    var place: PlaceResponse?
) : Parcelable