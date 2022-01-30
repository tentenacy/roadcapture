package com.untilled.roadcapture.data.datasource.api.dto.picture

import com.untilled.roadcapture.data.datasource.api.dto.place.PlaceCreateRequest
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class PictureCreateRequest(
    val thumbnail: Boolean = false,
    val order: Int,
    val description: String? = null,
    val place: PlaceCreateRequest,
    @Transient
    val path: String? = null
)