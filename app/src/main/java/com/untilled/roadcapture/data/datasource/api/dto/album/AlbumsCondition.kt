package com.untilled.roadcapture.data.datasource.api.dto.album

import java.time.LocalDateTime

data class AlbumsCondition(
    val dateTimeTo: String = "",
    val dateTimeFrom: String = "",
    val title: String = "",
)
