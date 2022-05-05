package com.untilled.roadcapture.features.root.followingalbums.dto

import com.untilled.roadcapture.features.common.dto.ItemClickArgs

data class FollowingAlbumsAdapterArgs(
    val followingFiltersViewHolderArgs: FollowingFiltersViewHolderArgs,
    val itemOnClickListener: (ItemClickArgs?) -> Unit
)