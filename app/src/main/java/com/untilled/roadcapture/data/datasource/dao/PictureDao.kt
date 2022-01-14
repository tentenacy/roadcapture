package com.untilled.roadcapture.data.datasource.dao

import androidx.room.*
import com.untilled.roadcapture.data.entity.Picture

@Dao
interface PictureDao {
    @Insert
    suspend fun insertPicture(picture: Picture)

    @Update
    suspend fun updatePicture(picture: Picture)

    @Delete
    suspend fun deletePicture(picture: Picture)

    @Query("select * from picture")
    suspend fun getPictures(): List<Picture>

    @Query("delete from picture")
    suspend fun deleteAll()
}