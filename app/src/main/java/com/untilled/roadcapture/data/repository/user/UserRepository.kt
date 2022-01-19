package com.untilled.roadcapture.data.repository.user

import com.untilled.roadcapture.data.datasource.api.dto.common.PageRequest
import com.untilled.roadcapture.data.datasource.api.dto.common.PageResponse
import com.untilled.roadcapture.data.datasource.api.dto.user.*
import com.untilled.roadcapture.data.entity.User
import com.untilled.roadcapture.utils.type.SocialType
import io.reactivex.rxjava3.core.Single

interface UserRepository {
    fun socialSignup(socialType: SocialType): Single<TokenResponse>
    fun login(request: LoginRequest): Single<TokenResponse>
    fun reissue(): Single<TokenResponse>
    fun getUserDetail(): Single<UserResponse>
    fun getUserInfo(id: Int): Single<UsersResponse>
    fun getUserFollower(followingsCondition: FollowingsCondition, pageRequest: PageRequest): Single<PageResponse<UsersResponse>>
    fun getUserFollowing(followingsCondition: FollowingsCondition, pageRequest: PageRequest): Single<PageResponse<UsersResponse>>
}