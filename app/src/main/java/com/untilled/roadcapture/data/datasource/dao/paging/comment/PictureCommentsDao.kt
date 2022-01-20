package com.untilled.roadcapture.data.datasource.dao.paging.comment

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.untilled.roadcapture.data.entity.paging.AlbumComments
import com.untilled.roadcapture.data.entity.paging.Albums
import com.untilled.roadcapture.data.entity.paging.PictureComments

@Dao
interface PictureCommentsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(albums: List<PictureComments.PictureComment>)

    @Query("SELECT * FROM picture_comments ORDER BY id ASC")
    fun selectAll(): PagingSource<Int, PictureComments.PictureComment>

    @Query("DELETE FROM picture_comments")
    fun clearComments()
}