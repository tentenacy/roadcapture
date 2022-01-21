package com.untilled.roadcapture.data.repository.follower

import com.untilled.roadcapture.data.datasource.api.RoadCaptureApi
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsResponse
import com.untilled.roadcapture.data.datasource.api.dto.common.PageRequest
import com.untilled.roadcapture.data.datasource.api.dto.common.PageResponse
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FollowRepositoryImpl @Inject constructor(
    private val roadCaptureApi: RoadCaptureApi
) : FollowRepository{
    override fun follow(id: Long): Single<Unit> = roadCaptureApi.follow(id)
    override fun getFollowingAlbums(id: Long?, pageRequest: PageRequest): Single<PageResponse<AlbumsResponse>> =
        roadCaptureApi.getFollowingAlbums(id,pageRequest.page,pageRequest.size,pageRequest.sort)

}