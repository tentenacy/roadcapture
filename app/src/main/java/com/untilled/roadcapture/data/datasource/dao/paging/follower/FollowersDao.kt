package com.untilled.roadcapture.data.datasource.dao.paging.follower

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.untilled.roadcapture.data.entity.paging.Albums
import com.untilled.roadcapture.data.entity.paging.Followers

@Dao
interface FollowersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(albums: List<Followers.Follower>)

    @Query("SELECT * FROM followers ORDER BY id ASC")
    fun selectAll(): PagingSource<Int, Followers.Follower>

    @Query("DELETE FROM followers")
    fun clearFollowers()
}