package com.untilled.roadcapture.data.datasource.api.dto.user

import android.os.Parcelable
import androidx.room.ColumnInfo
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UsersResponse(
    @ColumnInfo(name = "user_id")
    var id: Long,
    var username: String,
    var profileImageUrl: String,
    var backgroundImageUrl: String,
    var introduction: String,
    var followerCount: Int,
    var followingCount: Int,
    var followed: Boolean
) : Parcelable