package com.untilled.roadcapture.data.entity

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Picture(
    var imageUri: String? = null,
    var date: String? = null,
    @Embedded var searchResult : SearchResult? = null,
    var name : String? = null,
    var description : String? = null
) : Parcelable