package com.untilled.roadcapture.data.datasource.dao.paging.album

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.untilled.roadcapture.data.entity.paging.Albums
import com.untilled.roadcapture.data.entity.paging.UserAlbums

@Dao
interface UserAlbumsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(albums: List<UserAlbums.UserAlbum>)

    @Query("SELECT * FROM user_albums ORDER BY id ASC")
    fun selectAll(): PagingSource<Int, UserAlbums.UserAlbum>

    @Query("DELETE FROM user_albums")
    fun clearUserAlbums()
}