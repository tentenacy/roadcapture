package com.untilled.roadcapture.data.datasource.api.dto.user

import android.os.Parcelable
import androidx.room.ColumnInfo
import kotlinx.parcelize.Parcelize

@Parcelize
data class UsersResponse(
    @ColumnInfo(name = "user_id")
    val id: Long,
    val username: String,
    val profileImageUrl: String,
): Parcelable