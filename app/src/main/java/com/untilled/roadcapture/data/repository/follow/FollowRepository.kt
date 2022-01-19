package com.untilled.roadcapture.data.repository.follow

import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsResponse
import com.untilled.roadcapture.data.datasource.api.dto.common.PageRequest
import com.untilled.roadcapture.data.datasource.api.dto.common.PageResponse
import io.reactivex.rxjava3.core.Single

interface FollowRepository {
    fun follow(id: Int): Single<Unit>
    fun getFollowingAlbums(id: Int?, pageRequest: PageRequest): Single<PageResponse<AlbumsResponse>>
}