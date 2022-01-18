package com.untilled.roadcapture.data.datasource.api.dto.common

data class PageResponse<T>(
    var content: List<T>,
    var pageable: Pageable,
    val totalPages: Int,
    val totalElements: Int,
    val last: Boolean,
    val size: Int,
    val number: Int,
    val sort: Sort,
    val numberOfElements: Int,
    val first: Boolean,
    val empty: Boolean
)
