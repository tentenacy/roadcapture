package com.untilled.roadcapture.data.datasource.api.dto.place

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.untilled.roadcapture.data.datasource.api.dto.address.Address
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlaceResponse(
    @ColumnInfo(name = "place_id")
    val id: Long,
    val name: String,
    //TODO: Double로 받기
    val latitude: String,
    val longitude: String,
    @Embedded
    val address: Address
): Parcelable
