package com.untilled.roadcapture.data.entity

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsResponse
import com.untilled.roadcapture.data.datasource.api.dto.picture.PictureResponse
import com.untilled.roadcapture.data.datasource.api.dto.user.UsersResponse
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime

@Parcelize
data class AlbumsPage(
    val total: Int = 0,
    val page: Int = 0,
    val albums: List<Albums>
): Parcelable {

    @IgnoredOnParcel
    val endOfPage = total == page

    @Parcelize
    @Entity(tableName = "albums")
    data class Albums(
        @PrimaryKey(autoGenerate = true) val id: Long = 0,
        val albumsId: Long,
        var createdAt: String?,
        val lastModifiedAt: String?,
        val title: String,
        val description: String?,
        val thumbnailUrl: String?,
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