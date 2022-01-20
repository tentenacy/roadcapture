package com.untilled.roadcapture.data.datasource.api.dto.user

import android.os.Parcelable
import androidx.room.ColumnInfo
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UsersResponse(
    @ColumnInfo(name = "user_id")
    var id: Long,
    var profileImageUrl: String,
    var username: String,
    var introduction: String
) : Parcelable