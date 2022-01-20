package com.untilled.roadcapture.data.entity.paging

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.untilled.roadcapture.data.datasource.api.dto.picture.ThumbnailPictureResponse
import com.untilled.roadcapture.data.datasource.api.dto.user.UsersResponse
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime

@Parcelize
data class UserAlbums(
    val total: Int = 0,
    val page: Int = 0,
    val userAlbums: List<UserAlbum>
) : Parcelable {

    @IgnoredOnParcel
    val endOfPage = total - 1 == page

    @Parcelize
    @Entity(tableName = "user_albums")
    data class UserAlbum(
        @PrimaryKey(autoGenerate = true) val id: Long = 0,
        val userAlbumsId: Long,
        val createdAt: LocalDateTime,
        val lastModifiedAt: LocalDateTime,
        val title: String,
        @Embedded
        val thumbnailPicture: ThumbnailPictureResponse,
    ) : Parcelable

    @Parcelize
    @Entity(tableName = "user_albums_remote_keys")
    data class UserAlbumRemoteKeys(
        @PrimaryKey val userAlbumsId: Long,
        val prevKey: Int?,
        val nextKey: Int,
    ) : Parcelable
}