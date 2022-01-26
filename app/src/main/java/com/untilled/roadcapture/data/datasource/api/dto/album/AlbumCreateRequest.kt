package com.untilled.roadcapture.data.datasource.api.dto.album

import com.untilled.roadcapture.data.datasource.api.dto.picture.PictureCreateRequest

data class AlbumCreateRequest(
    val title: String,
    val description: String? = null,
    val pictures: List<PictureCreateRequest>
)