package com.untilled.roadcapture.data.datasource.dao.paging.follower

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.untilled.roadcapture.data.entity.paging.Albums
import com.untilled.roadcapture.data.entity.paging.Followers
import com.untilled.roadcapture.data.entity.paging.Followings
import com.untilled.roadcapture.data.entity.paging.FollowingsSortByAlbum

@Dao
interface FollowingsSortByAlbumDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(albums: List<FollowingsSortByAlbum.FollowingSortByAlbum>)

    @Query("SELECT * FROM followings_sort_by_album ORDER BY id ASC")
    fun selectAll(): PagingSource<Int, FollowingsSortByAlbum.FollowingSortByAlbum>

    @Query("DELETE FROM followings_sort_by_album")
    fun clearFollowingsSortByAlbum()
}