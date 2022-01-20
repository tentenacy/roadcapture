package com.untilled.roadcapture.data.datasource.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.untilled.roadcapture.data.datasource.dao.paging.album.AlbumsRemoteKeysDao
import com.untilled.roadcapture.data.datasource.dao.paging.album.AlbumsDao
import com.untilled.roadcapture.data.datasource.dao.paging.comment.AlbumCommentsDao
import com.untilled.roadcapture.data.datasource.dao.paging.comment.AlbumCommentsRemoteKeysDao
import com.untilled.roadcapture.data.entity.paging.AlbumComments
import com.untilled.roadcapture.data.entity.paging.Albums

@Database(
    entities = [
        Albums.Album::class,
        Albums.AlbumRemoteKeys::class,
        AlbumComments.AlbumComment::class,
        AlbumComments.AlbumCommentRemoteKeys::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class PagingDatabase : RoomDatabase() {
    abstract fun albumsDao(): AlbumsDao
    abstract fun albumsRemoteKeysDao(): AlbumsRemoteKeysDao
    abstract fun albumCommentsDao(): AlbumCommentsDao
    abstract fun albumCommentsKeysDao(): AlbumCommentsRemoteKeysDao

    companion object {
        fun getInstance(context: Context): PagingDatabase =
            Room.databaseBuilder(context, PagingDatabase::class.java, "paging_db").build()
    }
}