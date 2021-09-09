package com.untilled.roadcapture.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.untilled.roadcapture.data.entity.PictureThumbnail

@Dao
interface PictureThumbnailDao {
    @Query("SELECT * FROM PictureThumbnail")
    fun getAll(): List<PictureThumbnail>

    @Insert
    fun insertInfoWindow(infoWindow: PictureThumbnail)

    @Query("DELETE FROM PictureThumbnail")
    fun deleteAll()

    @Delete
    fun delete(infoWindow: PictureThumbnail)
}