package com.untilled.roadcapture.data.datasource.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsResponse
import com.untilled.roadcapture.data.entity.AlbumsPage

@Dao
interface AlbumsRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(remoteKey: List<AlbumsPage.AlbumRemoteKeys>)

    @Query("SELECT * FROM albums_remote_keys WHERE albumsId = :albumsId")
    fun remoteKeysByAlbumsId(albumsId: Long): AlbumsPage.AlbumRemoteKeys

    @Query("DELETE FROM albums_remote_keys")
    fun clearRemoteKeys()
}