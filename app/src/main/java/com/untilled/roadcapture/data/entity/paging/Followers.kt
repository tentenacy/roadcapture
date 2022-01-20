package com.untilled.roadcapture.data.entity.paging

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.untilled.roadcapture.data.datasource.api.dto.user.UsersResponse
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Followers(
    val total: Int = 0,
    val page: Int = 0,
    val followers: List<Follower>
): Parcelable {

    @IgnoredOnParcel
    val endOfPage = total - 1 == page

    @Parcelize
    @Entity(tableName = "followers")
    data class Follower(
        @PrimaryKey(autoGenerate = true) val id: Long = 0,
        var followerId: Long,
        var profileImageUrl: String,
        var username: String,
        var introduction: String,
    ): Parcelable

    @Parcelize
    @Entity(tableName = "followers_remote_keys")
    data class FollowerRemoteKeys(
        @PrimaryKey val followersId: Long,
        val prevKey: Int?,
        val nextKey: Int,
    ): Parcelable
}