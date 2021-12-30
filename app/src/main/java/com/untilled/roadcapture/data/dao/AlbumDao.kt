package com.untilled.roadcapture.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.untilled.roadcapture.data.entity.Album

@Dao
interface AlbumDao {
    @Insert
    suspend fun insertAlbum(album: Album)

    @Update
    suspend fun updateAlbum(album: Album)

    @Delete
    suspend fun deleteAlbum(album: Album)
}