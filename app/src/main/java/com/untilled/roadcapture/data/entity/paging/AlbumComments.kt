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
data class AlbumComments(
    val total: Int = 0,
    val page: Int = 0,
    val albumComments: List<AlbumComment>
): Parcelable {

    @IgnoredOnParcel
    val endOfPage = total - 1 == page

    @Parcelize
    @Entity(tableName = "album_comments")
    data class AlbumComment(
        @PrimaryKey(autoGenerate = true) val id: Long = 0,
        val albumCommentsId: Long,
        val pictureId: Long,
        val createdAt: LocalDateTime,
        val lastModifiedAt: LocalDateTime,
        val content: String,
        @Embedded
        val user: UsersResponse
    ): Parcelable

    @Parcelize
    @Entity(tableName = "album_comments_remote_keys")
    data class AlbumCommentRemoteKeys(
        @PrimaryKey val albumCommentsId: Long,
        val prevKey: Int?,
        val nextKey: Int,
    ): Parcelable

}