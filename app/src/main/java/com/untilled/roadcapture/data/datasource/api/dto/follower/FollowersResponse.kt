package com.untilled.roadcapture.data.datasource.api.dto.follower

import android.os.Parcelable
import com.untilled.roadcapture.data.datasource.api.dto.address.Address
import kotlinx.parcelize.Parcelize

@Parcelize
data class FollowersResponse(
    val id: Long,
    val username: String,
    val profileImageUrl: String,
    val followed: Boolean,
): Parcelable
