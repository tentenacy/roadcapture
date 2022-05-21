package com.untilled.roadcapture.data.repository.follower

import com.untilled.roadcapture.data.datasource.api.RoadCaptureApi
import com.untilled.roadcapture.utils.retryThreeTimes
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FollowRepositoryImpl @Inject constructor(
    private val roadCaptureApi: RoadCaptureApi
) : FollowRepository{
    override fun follow(toUserId: Long): Single<Unit> = roadCaptureApi.follow(toUserId)
    override fun unfollow(toUserId: Long): Single<Unit> = roadCaptureApi.unfollow(toUserId)
}