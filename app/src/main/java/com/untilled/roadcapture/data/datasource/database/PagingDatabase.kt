package com.untilled.roadcapture.data.datasource.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.untilled.roadcapture.data.datasource.dao.AlbumsRemoteKeysDao
import com.untilled.roadcapture.data.datasource.dao.AlbumsRxDao
import com.untilled.roadcapture.data.datasource.dao.PictureDao
import com.untilled.roadcapture.data.entity.AlbumsPage
import com.untilled.roadcapture.data.entity.Picture

@Database(
    entities = [
        AlbumsPage.Albums::class,
        AlbumsPage.AlbumRemoteKeys::class
    ],
    version = 1,
    exportSchema = false
)
abstract class PagingDatabase : RoomDatabase() {
    abstract fun albumsRxDao(): AlbumsRxDao
    abstract fun albumsRemoteKeysRxDao(): AlbumsRemoteKeysDao

    companion object {
        fun getInstance(context: Context): PagingDatabase =
            Room.databaseBuilder(context, PagingDatabase::class.java, "paging_db").build()
    }
}