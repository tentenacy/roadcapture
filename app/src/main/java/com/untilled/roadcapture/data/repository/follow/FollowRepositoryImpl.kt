package com.untilled.roadcapture.data.repository.follow

import com.untilled.roadcapture.data.datasource.api.RoadCaptureApi
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class FollowRepositoryImpl @Inject constructor(
    private val roadCaptureApi: RoadCaptureApi
) : FollowRepository{
    override fun follow(id: Int, token: String): Single<Unit> = roadCaptureApi.follow(id,token)

}