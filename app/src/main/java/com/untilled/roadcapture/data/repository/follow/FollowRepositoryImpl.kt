package com.untilled.roadcapture.data.repository.follow

import com.untilled.roadcapture.data.datasource.api.RoadCaptureApi
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumResponse
import com.untilled.roadcapture.data.datasource.api.dto.common.PageRequest
import com.untilled.roadcapture.data.datasource.api.dto.common.PageResponse
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class FollowRepositoryImpl @Inject constructor(
    private val roadCaptureApi: RoadCaptureApi
) : FollowRepository{
    override fun follow(id: Int): Single<Unit> = roadCaptureApi.follow(id)
    override fun getFollowingAlbums(id: Int, pageRequest: PageRequest): Single<PageResponse<AlbumResponse>> =
        roadCaptureApi.getFollowingAlbums(id,pageRequest.page,pageRequest.size,pageRequest.sort)

}