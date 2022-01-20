package com.untilled.roadcapture.data.datasource.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.untilled.roadcapture.data.datasource.dao.paging.album.AlbumsRemoteKeysDao
import com.untilled.roadcapture.data.datasource.dao.paging.album.AlbumsDao
import com.untilled.roadcapture.data.datasource.dao.paging.album.UserAlbumsDao
import com.untilled.roadcapture.data.datasource.dao.paging.album.UserAlbumsRemoteKeysDao
import com.untilled.roadcapture.data.datasource.dao.paging.comment.AlbumCommentsDao
import com.untilled.roadcapture.data.datasource.dao.paging.comment.AlbumCommentsRemoteKeysDao
import com.untilled.roadcapture.data.datasource.dao.paging.comment.PictureCommentsDao
import com.untilled.roadcapture.data.datasource.dao.paging.comment.PictureCommentsRemoteKeysDao
import com.untilled.roadcapture.data.datasource.dao.paging.follower.FollowersDao
import com.untilled.roadcapture.data.datasource.dao.paging.follower.FollowersRemoteKeysDao
import com.untilled.roadcapture.data.entity.paging.*
import com.untilled.roadcapture.utils.converter.RoomConverters

@Database(
    entities = [
        Albums.Album::class,
        Albums.AlbumRemoteKeys::class,
        AlbumComments.AlbumComment::class,
        AlbumComments.AlbumCommentRemoteKeys::class,
        PictureComments.PictureComment::class,
        PictureComments.PictureCommentRemoteKeys::class,
        UserAlbums.UserAlbum::class,
        UserAlbums.UserAlbumRemoteKeys::class,
        Followers.Follower::class,
        Followers.FollowerRemoteKeys::class,
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(RoomConverters::class)
abstract class PagingDatabase : RoomDatabase() {
    abstract fun albumsDao(): AlbumsDao
    abstract fun albumsRemoteKeysDao(): AlbumsRemoteKeysDao
    abstract fun albumCommentsDao(): AlbumCommentsDao
    abstract fun albumCommentsKeysDao(): AlbumCommentsRemoteKeysDao
    abstract fun pictureCommentsDao(): PictureCommentsDao
    abstract fun pictureCommentsKeysDao(): PictureCommentsRemoteKeysDao
    abstract fun userAlbumsDao(): UserAlbumsDao
    abstract fun userAlbumsKeysDao(): UserAlbumsRemoteKeysDao
    abstract fun followersDao(): FollowersDao
    abstract fun followersKeysDao(): FollowersRemoteKeysDao

    companion object {
        fun getInstance(context: Context): PagingDatabase =
            Room.databaseBuilder(context, PagingDatabase::class.java, "paging_db").build()
    }
}