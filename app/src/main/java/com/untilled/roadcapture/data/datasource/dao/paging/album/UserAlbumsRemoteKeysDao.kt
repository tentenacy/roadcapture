package com.untilled.roadcapture.data.datasource.dao.paging.album

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.untilled.roadcapture.data.entity.paging.Albums
import com.untilled.roadcapture.data.entity.paging.UserAlbums

@Dao
interface UserAlbumsRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(remoteKey: List<UserAlbums.UserAlbumRemoteKeys>)

    @Query("SELECT * FROM user_albums_remote_keys WHERE userAlbumId = :userAlbumsId")
    fun remoteKeysByUserAlbumsId(userAlbumsId: Long): UserAlbums.UserAlbumRemoteKeys

    @Query("DELETE FROM user_albums_remote_keys")
    fun clearRemoteKeys()
}