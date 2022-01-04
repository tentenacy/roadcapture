package com.untilled.roadcapture.data.dto.place

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.untilled.roadcapture.data.dto.address.Address
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Place(
    @ColumnInfo(name = "place_id")
    val id: Int,
    val name: String,
    val latitude: String,
    val longitude: String,
    @Embedded
    val address: Address
): Parcelable
