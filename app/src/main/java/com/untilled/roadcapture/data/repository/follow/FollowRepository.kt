package com.untilled.roadcapture.data.repository.follow

import io.reactivex.rxjava3.core.Single

interface FollowRepository {
    fun follow(id: Int, token: String): Single<Unit>
}