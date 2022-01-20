package com.untilled.roadcapture.data.datasource.dao.paging.follower

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.untilled.roadcapture.data.entity.paging.Albums
import com.untilled.roadcapture.data.entity.paging.Followers

@Dao
interface FollowersRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(remoteKey: List<Followers.FollowerRemoteKeys>)

    @Query("SELECT * FROM followers_remote_keys WHERE followersId = :followersId")
    fun remoteKeysByFollowersId(followersId: Long): Followers.FollowerRemoteKeys

    @Query("DELETE FROM followers_remote_keys")
    fun clearRemoteKeys()
}