package com.untilled.roadcapture.data.datasource.api.dto.place

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.untilled.roadcapture.data.datasource.api.dto.address.Address
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlaceResponse(
    @ColumnInfo(name = "place_id")
    var id: Long,
    var name: String,
    var latitude: String,
    var longitude: String,
    @Embedded
    var address: Address
): Parcelable
