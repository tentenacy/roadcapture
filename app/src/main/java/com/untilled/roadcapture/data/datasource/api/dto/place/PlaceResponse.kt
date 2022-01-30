package com.untilled.roadcapture.data.datasource.api.dto.place

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.untilled.roadcapture.data.datasource.api.dto.address.Address
import kotlinx.android.parcel.Parcelize
@Parcelize
data class PlaceResponse(
    val id: Long,
    val name: String,
    val latitude: String,
    val longitude: String,
    val address: Address
) : Parcelable
