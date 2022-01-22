package com.untilled.roadcapture.data.datasource.dao.paging.follower

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.untilled.roadcapture.data.entity.paging.Albums
import com.untilled.roadcapture.data.entity.paging.Followers
import com.untilled.roadcapture.data.entity.paging.Followings

@Dao
interface FollowingsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(albums: List<Followings.Following>)

    @Query("SELECT * FROM followings ORDER BY id ASC")
    fun selectAll(): PagingSource<Int, Followings.Following>

    @Query("DELETE FROM followings")
    fun clearFollowings()
}