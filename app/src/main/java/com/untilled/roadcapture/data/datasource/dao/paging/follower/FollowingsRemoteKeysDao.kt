package com.untilled.roadcapture.data.datasource.dao.paging.follower

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.untilled.roadcapture.data.entity.paging.Albums
import com.untilled.roadcapture.data.entity.paging.Followers
import com.untilled.roadcapture.data.entity.paging.Followings

@Dao
interface FollowingsRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(remoteKey: List<Followings.FollowingRemoteKeys>)

    @Query("SELECT * FROM followings_remote_keys WHERE followingId = :followingId")
    fun remoteKeysByFollowingsId(followingId: Long): Followings.FollowingRemoteKeys

    @Query("DELETE FROM followings_remote_keys")
    fun clearRemoteKeys()
}