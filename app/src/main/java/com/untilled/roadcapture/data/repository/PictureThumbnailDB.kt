package com.untilled.roadcapture.data.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.untilled.roadcapture.data.dao.PictureThumbnailDao
import com.untilled.roadcapture.data.entity.PictureThumbnail

@Database(entities = [PictureThumbnail::class], version = 1, exportSchema = false)
abstract class PictureThumbnailDB : RoomDatabase() {
    abstract fun infoWindowDao() : PictureThumbnailDao
}