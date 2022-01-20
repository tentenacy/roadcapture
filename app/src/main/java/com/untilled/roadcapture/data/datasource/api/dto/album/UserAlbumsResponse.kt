package com.untilled.roadcapture.data.datasource.api.dto.album

import com.untilled.roadcapture.data.datasource.api.dto.picture.ThumbnailPictureResponse
import java.time.LocalDateTime

data class UserAlbumsResponse (
    val id: Long,
    val createdAt: LocalDateTime,
    val lastModifiedAt: LocalDateTime,
    val title: String,
    val thumbnailPicture: ThumbnailPictureResponse
)