package com.untilled.roadcapture.data.datasource.api.dto.user

import android.os.Parcelable
import androidx.room.ColumnInfo
import com.untilled.roadcapture.data.datasource.api.dto.address.Address
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDetailResponse(
    @ColumnInfo(name = "user_id")
    val id: Long,
    val email: String,
    val username: String,
    val profileImageUrl: String,
    val introduction: String? = null,
    val provider: String? = null,
    val address: Address?
): Parcelable
