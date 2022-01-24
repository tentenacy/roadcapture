package com.untilled.roadcapture.data.entity.paging

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.untilled.roadcapture.data.datasource.api.dto.user.UsersResponse
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Albums(
    val total: Int = 0,
    val page: Int = 0,
    val albums: List<Album>
): Parcelable {

    @IgnoredOnParcel
    val endOfPage = total - 1 <= page

    @Parcelize
    @Entity(tableName = "albums")
    data class Album(
        @PrimaryKey(autoGenerate = true) val id: Long = 0,
        val albumsId: Long,
        var createdAt: LocalDateTime,
        val lastModifiedAt: LocalDateTime,
        val title: String,
        val description: String,
        val thumbnailUrl: String,
        @Embedded
        val user: UsersResponse,
        val viewCount: Int,
        val likeCount: Int,
        val commentCount: Int,
        val liked: Boolean
    ) : Parcelable

    @Parcelize
    @Entity(tableName = "albums_remote_keys")
    data class AlbumRemoteKeys(
        @PrimaryKey val albumsId: Long,
        val prevKey: Int?,
        val nextKey: Int,
    ): Parcelable
}