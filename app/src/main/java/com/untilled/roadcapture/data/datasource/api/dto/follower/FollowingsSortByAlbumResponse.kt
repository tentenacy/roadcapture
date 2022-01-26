package com.untilled.roadcapture.data.datasource.api.dto.follower

import androidx.room.ColumnInfo
import java.time.LocalDateTime

data class FollowingsSortByAlbumResponse(
    val id: Long,
    val username: String,
    val profileImageUrl: String,
    val latestAlbumCreatedAt: LocalDateTime,
    val latestAlbumLastModifiedAt: LocalDateTime,
)
