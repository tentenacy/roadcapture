package com.untilled.roadcapture.data.datasource.api.dto.picture

import com.untilled.roadcapture.data.datasource.api.dto.place.PlaceResponse
import java.time.LocalDateTime

data class ThumbnailPictureResponse (
    var id: Long,
    var createdAt: LocalDateTime?,
    var lastModifiedAt: LocalDateTime?,
    var imageUrl: String?,
    var place: PlaceResponse?
)
