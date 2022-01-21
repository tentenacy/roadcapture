package com.untilled.roadcapture.data.datasource.api.dto.user

data class FollowingsCondition(
    var userId: Long,
    var username: String? = null
)
