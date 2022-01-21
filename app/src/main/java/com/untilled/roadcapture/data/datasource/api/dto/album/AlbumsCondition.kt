package com.untilled.roadcapture.data.datasource.api.dto.album

import java.time.LocalDateTime

data class AlbumsCondition(
    val dateTimeTo: String? = null,
    val dateTimeFrom: String? = null,
    val title: String? = null,
)
