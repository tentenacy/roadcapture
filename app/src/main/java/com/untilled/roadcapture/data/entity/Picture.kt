package com.untilled.roadcapture.data.entity

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Picture(
    var imageUri: String?,
    var date: String?,
    @Embedded var searchResult : SearchResult?,
    var name : String?,
    var description : String?
) : Parcelable