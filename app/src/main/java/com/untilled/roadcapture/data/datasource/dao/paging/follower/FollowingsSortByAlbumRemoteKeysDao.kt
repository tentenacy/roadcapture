package com.untilled.roadcapture.data.datasource.dao.paging.follower

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.untilled.roadcapture.data.entity.paging.Albums
import com.untilled.roadcapture.data.entity.paging.Followers
import com.untilled.roadcapture.data.entity.paging.Followings
import com.untilled.roadcapture.data.entity.paging.FollowingsSortByAlbum

@Dao
interface FollowingsSortByAlbumRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(remoteKey: List<FollowingsSortByAlbum.FollowingSortByAlbumRemoteKeys>)

    @Query("SELECT * FROM followings_sort_by_album_remote_keys WHERE followingSortByAlbumId = :followingSortByAlbumId")
    fun remoteKeysByFollowingSortByAlbumId(followingSortByAlbumId: Long): FollowingsSortByAlbum.FollowingSortByAlbumRemoteKeys

    @Query("DELETE FROM followings_sort_by_album_remote_keys")
    fun clearRemoteKeys()
}