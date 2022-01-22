package com.untilled.roadcapture.data.datasource.api.dto.common

data class PageRequest(
    val page: Int? = null,
    val size: Int? = null,
    val sort: String? = null,
)
