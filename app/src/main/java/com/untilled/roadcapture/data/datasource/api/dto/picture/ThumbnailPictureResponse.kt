package com.untilled.roadcapture.data.datasource.api.dto.picture

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.untilled.roadcapture.data.datasource.api.dto.place.PlaceResponse
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime

@Parcelize
data class ThumbnailPictureResponse (
    @ColumnInfo(name = "thumbnail_picture_id")
    val id: Long,
    @ColumnInfo(name = "thumbnail_picture_created_at")
    val createdAt: LocalDateTime,
    @ColumnInfo(name = "thumbnail_picture_last_modified_at")
    val lastModifiedAt: LocalDateTime,
    val imageUrl: String,
    @Embedded
    val place: PlaceResponse
): Parcelable
