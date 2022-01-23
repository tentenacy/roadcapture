package com.untilled.roadcapture.data.repository.user

import com.untilled.roadcapture.data.datasource.api.dto.common.PageRequest
import com.untilled.roadcapture.data.datasource.api.dto.common.PageResponse
import com.untilled.roadcapture.data.datasource.api.dto.address.PlaceCondition
import com.untilled.roadcapture.data.datasource.api.dto.album.UserAlbumsResponse
import com.untilled.roadcapture.data.datasource.api.dto.user.*
import com.untilled.roadcapture.utils.type.SocialType
import io.reactivex.rxjava3.core.Single

interface UserRepository {
    fun signup(signupRequest: SignupRequest): Single<TokenResponse>
    fun socialSignup(socialType: SocialType): Single<TokenResponse>
    fun login(loginRequest: LoginRequest): Single<TokenResponse>
    fun reissue(): Single<TokenResponse>
    fun getMyInfo(): Single<UsersResponse>
    fun getUserDetail(): Single<UserResponse>
    fun getUserInfo(userId: Long): Single<UsersResponse>
    fun getUserAlbums(userId: Long?, pageRequest: PageRequest, placeCondition: PlaceCondition): Single<PageResponse<UserAlbumsResponse>>
    fun getUserFollower(userId: Long, followingsCondition: FollowingsCondition, pageRequest: PageRequest): Single<PageResponse<UsersResponse>>
    fun getUserFollowing(userId: Long, followingsCondition: FollowingsCondition, pageRequest: PageRequest): Single<PageResponse<UsersResponse>>
}