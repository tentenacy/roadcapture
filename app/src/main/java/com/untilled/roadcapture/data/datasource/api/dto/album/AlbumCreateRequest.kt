package com.untilled.roadcapture.data.datasource.api.dto.album

import com.untilled.roadcapture.data.datasource.api.dto.picture.PictureCreateRequest
import kotlinx.serialization.Serializable

@Serializable
data class AlbumCreateRequest(
    val title: String,
    val description: String? = null,
    var pictures: List<PictureCreateRequest>? = null
)