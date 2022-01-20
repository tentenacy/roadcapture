package com.untilled.roadcapture.data.datasource.dao.paging.comment

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.untilled.roadcapture.data.entity.paging.AlbumComments
import com.untilled.roadcapture.data.entity.paging.Albums
import com.untilled.roadcapture.data.entity.paging.PictureComments

@Dao
interface PictureCommentsRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(remoteKey: List<PictureComments.PictureCommentRemoteKeys>)

    @Query("SELECT * FROM picture_comments_remote_keys WHERE albumCommentsId = :albumCommentsId")
    fun remoteKeysByAlbumCommentsId(albumCommentsId: Long): PictureComments.PictureCommentRemoteKeys

    @Query("DELETE FROM picture_comments_remote_keys")
    fun clearRemoteKeys()
}