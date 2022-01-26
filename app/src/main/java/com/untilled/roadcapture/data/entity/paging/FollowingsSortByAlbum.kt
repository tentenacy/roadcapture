package com.untilled.roadcapture.data.entity.paging

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime

@Parcelize
data class FollowingsSortByAlbum(
    val total: Int = 0,
    val page: Int = 0,
    val followingsSortByAlbum: List<FollowingSortByAlbum>
): Parcelable {

    @IgnoredOnParcel
    val endOfPage = total - 1 <= page

    @Parcelize
    @Entity(tableName = "followings_sort_by_album")
    data class FollowingSortByAlbum(
        @PrimaryKey(autoGenerate = true) val id: Long = 0,
        val followingSortByAlbumId: Long,
        val profileImageUrl: String,
        val username: String,
        val latestAlbumCreatedAt: LocalDateTime,
        val latestAlbumLastModifiedAt: LocalDateTime,
    ): Parcelable

    @Parcelize
    @Entity(tableName = "followings_sort_by_album_remote_keys")
    data class FollowingSortByAlbumRemoteKeys(
        @PrimaryKey val followingSortByAlbumId: Long,
        val prevKey: Int?,
        val nextKey: Int,
    ): Parcelable
}