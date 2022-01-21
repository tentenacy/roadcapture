package com.untilled.roadcapture.data.repository.follower

import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsResponse
import com.untilled.roadcapture.data.datasource.api.dto.common.PageRequest
import com.untilled.roadcapture.data.datasource.api.dto.common.PageResponse
import io.reactivex.rxjava3.core.Single

interface FollowRepository {
    fun follow(id: Long): Single<Unit>
    fun getFollowingAlbums(id: Long?, pageRequest: PageRequest): Single<PageResponse<AlbumsResponse>>
}