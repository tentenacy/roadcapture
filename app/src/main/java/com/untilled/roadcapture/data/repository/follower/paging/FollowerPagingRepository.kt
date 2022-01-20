package com.untilled.roadcapture.data.repository.follower.paging

import androidx.paging.PagingData
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsCondition
import com.untilled.roadcapture.data.datasource.api.dto.album.FollowersCondition
import com.untilled.roadcapture.data.entity.paging.AlbumComments
import com.untilled.roadcapture.data.entity.paging.Albums
import com.untilled.roadcapture.data.entity.paging.Followers
import com.untilled.roadcapture.data.entity.paging.PictureComments
import io.reactivex.rxjava3.core.Flowable

interface FollowerPagingRepository {
    fun getFollowers(
        userId: Long,
        followersCondition: FollowersCondition?,
    ): Flowable<PagingData<Followers.Follower>>
}