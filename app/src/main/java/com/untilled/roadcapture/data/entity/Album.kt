package com.untilled.roadcapture.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Album(
    @PrimaryKey
    @SerializedName("id")
    var id: String,
    @SerializedName("username")
    var username: String,
    @SerializedName("profile_url")
    var profileUrl: String,
    @SerializedName("like_count")
    var likeCount: String,
    @SerializedName("comment_count")
    var commentCount: String,
    @SerializedName("title")
    var title: String,
    @SerializedName("description")
    var description: String,
    @SerializedName("created_date")
    var createdDate: String,
    @SerializedName("modified_date")
    var modifiedDate: String,
    @SerializedName("thumbnail_url")
    var thumbnailUrl: String
) : Parcelable