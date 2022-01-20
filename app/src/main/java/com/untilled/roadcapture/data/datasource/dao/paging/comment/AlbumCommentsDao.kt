package com.untilled.roadcapture.data.datasource.dao.paging.comment

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.untilled.roadcapture.data.entity.paging.AlbumComments
import com.untilled.roadcapture.data.entity.paging.Albums

@Dao
interface AlbumCommentsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(albums: List<AlbumComments.AlbumComment>)

    @Query("SELECT * FROM album_comments ORDER BY id ASC")
    fun selectAll(): PagingSource<Int, AlbumComments.AlbumComment>

    @Query("DELETE FROM album_comments")
    fun clearAlbums()
}