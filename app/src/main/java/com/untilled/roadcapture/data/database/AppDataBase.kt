package com.untilled.roadcapture.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.untilled.roadcapture.data.dao.AlbumDao
import com.untilled.roadcapture.data.entity.Album

@Database(entities = [Album::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase(){
    abstract  fun albumDao(): AlbumDao

    companion object{
        fun getInstance(context: Context) : AppDataBase =
            Room.databaseBuilder(context,AppDataBase::class.java,"album_db").build()
    }
}