package com.untilled.roadcapture.data.datasource.dao.paging.comment

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.untilled.roadcapture.data.entity.paging.AlbumComments
import com.untilled.roadcapture.data.entity.paging.Albums

@Dao
interface AlbumCommentsRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(remoteKey: List<AlbumComments.AlbumCommentRemoteKeys>)

    @Query("SELECT * FROM album_comments_remote_keys WHERE albumCommentsId = :albumCommentsId")
    fun remoteKeysByAlbumCommentsId(albumCommentsId: Long): AlbumComments.AlbumCommentRemoteKeys

    @Query("DELETE FROM album_comments_remote_keys")
    fun clearRemoteKeys()
}