package com.untilled.roadcapture.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Picture(
    var imageUri: String?,
    var date: String,
    var searchResult : SearchResult,
    var name : String,
    var description : String
) : Parcelable