package com.untilled.roadcapture.data.datasource.dao.paging.album

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.untilled.roadcapture.data.entity.paging.Albums

@Dao
interface AlbumsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(albums: List<Albums.Album>)

    @Query("SELECT * FROM albums ORDER BY id ASC")
    fun selectAll(): PagingSource<Int, Albums.Album>

    @Query("DELETE FROM albums")
    fun clearAlbums()
}