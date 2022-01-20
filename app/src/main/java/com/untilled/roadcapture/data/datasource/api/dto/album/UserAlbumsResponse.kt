package com.untilled.roadcapture.data.datasource.api.dto.album

import com.untilled.roadcapture.data.datasource.api.dto.picture.ThumbnailPictureResponse
import java.time.LocalDateTime

data class UserAlbumsResponse (
    var id: Int,
    var createdAt: LocalDateTime?,
    var lastModifiedAt: LocalDateTime?,
    var title: String? = null,
    var thumbnailPicture: ThumbnailPictureResponse?
)