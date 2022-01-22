package com.untilled.roadcapture.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.untilled.roadcapture.data.datasource.api.dto.place.PlaceRequest
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Picture(
    @ColumnInfo(name = "order")
    var order: Long = 0L,
    var thumbnail: Boolean = false,
    var createdAt: String? = null,
    var lastModifiedAt: String? = null,
    var imageUrl: String? = null,
    var description: String? = null,
    @Embedded
    var place: PlaceRequest? = null
)  : Parcelable {
    @ColumnInfo(name = "picture_id")
    @PrimaryKey(autoGenerate = true) var id: Long = 0
}