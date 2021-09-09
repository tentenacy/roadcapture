package com.untilled.roadcapture.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PictureThumbnail(
    @PrimaryKey val uid : Int?,
    @Embedded val picture : Picture?
)
