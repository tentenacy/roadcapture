package com.untilled.roadcapture.data.datasource.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsResponse
import com.untilled.roadcapture.data.entity.AlbumsPage

@Dao
interface AlbumsRxDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(albums: List<AlbumsPage.Albums>)

    @Query("SELECT * FROM albums ORDER BY id ASC")
    fun selectAll(): PagingSource<Int, AlbumsPage.Albums>

    @Query("DELETE FROM albums")
    fun clearAlbums()
}