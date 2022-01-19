package com.untilled.roadcapture.data.datasource.api.dto.common

data class PageRequest(
    var page: Int? = null,
    var size: Int? = null,
    var sort: String? = null,
)
