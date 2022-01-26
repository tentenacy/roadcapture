package com.untilled.roadcapture.data.datasource.api.dto.picture

import com.untilled.roadcapture.data.datasource.api.dto.place.PlaceCreateRequest

data class PictureCreateRequest(
    val thumbnail: Boolean = false,
    var createdAt: String,
    var lastModifiedAt: String,
    val description: String? = null,
    val place: PlaceCreateRequest,
    val imageUrl: String? = null
)