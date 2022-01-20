package com.untilled.roadcapture.data.entity.paging

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.untilled.roadcapture.data.datasource.api.dto.user.UsersResponse
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PictureComments(
    val total: Int = 0,
    val page: Int = 0,
    val pictureComments: List<PictureComment>
): Parcelable {

    @IgnoredOnParcel
    val endOfPage = total - 1 == page

    @Parcelize
    @Entity(tableName = "picture_comments")
    data class PictureComment(
        @PrimaryKey(autoGenerate = true) val id: Long = 0,
        val albumCommentsId: Long,
        val pictureId: Long,
        val createdAt: String,
        val lastModifiedAt: String,
        val content: String,
        @Embedded
        val user: UsersResponse
    ): Parcelable

    @Parcelize
    @Entity(tableName = "picture_comments_remote_keys")
    data class PictureCommentRemoteKeys(
        @PrimaryKey val albumCommentsId: Long,
        val prevKey: Int?,
        val nextKey: Int,
    ): Parcelable

}