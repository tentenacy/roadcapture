package com.untilled.roadcapture.data.datasource.api.dto.user

import com.untilled.roadcapture.data.datasource.api.dto.common.Pageable
import com.untilled.roadcapture.data.datasource.api.dto.common.Sort

data class UserFollowResponse(
    var content: List<Users>,
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
