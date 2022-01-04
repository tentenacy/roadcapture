package com.untilled.roadcapture.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.untilled.roadcapture.data.dao.PictureDao
import com.untilled.roadcapture.data.entity.Picture

@Database(entities = [Picture::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun pictureDao() : PictureDao

    companion object {
        fun getInstance(context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, "picture_db").build()
    }
}