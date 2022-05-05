package com.untilled.roadcapture.features.root.followingalbums.dto

import androidx.lifecycle.Lifecycle
import androidx.paging.CombinedLoadStates
import com.untilled.roadcapture.features.common.dto.ItemClickArgs

data class FollowingFiltersViewHolderArgs(
    val lifecycle: Lifecycle,
    val headerLoadStateListener: (CombinedLoadStates) -> Unit,
    val filterItemOnClickListener: (ItemClickArgs?) -> Unit,
)
