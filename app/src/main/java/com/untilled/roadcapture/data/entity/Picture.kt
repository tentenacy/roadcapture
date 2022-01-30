package com.untilled.roadcapture.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.untilled.roadcapture.data.datasource.api.dto.picture.PictureCreateRequest
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Picture(
    @ColumnInfo(name = "order")
    var order: Int = 0,
    var thumbnail: Boolean = false,
    var fileUri: String? = null,
    var description: String? = null,
    @Embedded
    var place: Place? = null
)  : Parcelable {
    @ColumnInfo(name = "picture_id")
    @PrimaryKey(autoGenerate = true) var id: Long = 0

    fun toPictureCreateRequest() : PictureCreateRequest =
        PictureCreateRequest(
            thumbnail = thumbnail,
            path = fileUri,
            description = description,
            place = place!!.toPlaceCreateRequest(),
            order = order
        )
}