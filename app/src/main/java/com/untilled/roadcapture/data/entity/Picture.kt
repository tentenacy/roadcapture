package com.untilled.roadcapture.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.untilled.roadcapture.data.api.dto.place.Place
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Picture(
    var createdAt: String? = null,
    var lastModifiedAt: String? = null,
    var imageUrl: String? = null,
    var description: String? = null,
    @Embedded
    var place: Place? = null
)  : Parcelable {
    @ColumnInfo(name = "picture_id")
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}