package com.untilled.roadcapture.data.datasource.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.untilled.roadcapture.data.datasource.dao.PictureDao
import com.untilled.roadcapture.data.entity.Picture

@Database(
    entities = [
        Picture::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class PictureDatabase : RoomDatabase() {
    abstract fun pictureDao(): PictureDao

    companion object {
        fun getInstance(context: Context): PictureDatabase =
            Room.databaseBuilder(context, PictureDatabase::class.java, "picture_db").build()
    }
}