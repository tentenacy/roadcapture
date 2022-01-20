package com.untilled.roadcapture.data.datasource.dao.paging.album

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.untilled.roadcapture.data.entity.paging.Albums

@Dao
interface AlbumsRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(remoteKey: List<Albums.AlbumRemoteKeys>)

    @Query("SELECT * FROM albums_remote_keys WHERE albumsId = :albumsId")
    fun remoteKeysByAlbumsId(albumsId: Long): Albums.AlbumRemoteKeys

    @Query("DELETE FROM albums_remote_keys")
    fun clearRemoteKeys()
}