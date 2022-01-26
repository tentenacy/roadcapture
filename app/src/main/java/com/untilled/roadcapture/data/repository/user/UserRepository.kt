package com.untilled.roadcapture.data.repository.user

import com.untilled.roadcapture.data.datasource.api.dto.common.PageRequest
import com.untilled.roadcapture.data.datasource.api.dto.common.PageResponse
import com.untilled.roadcapture.data.datasource.api.dto.address.PlaceCondition
import com.untilled.roadcapture.data.datasource.api.dto.album.UserAlbumsResponse
import com.untilled.roadcapture.data.datasource.api.dto.follower.FollowingsCondition
import com.untilled.roadcapture.data.datasource.api.dto.user.*
import com.untilled.roadcapture.utils.type.SocialType
import io.reactivex.rxjava3.core.Single

interface UserRepository {
    fun signup(signupRequest: SignupRequest): Single<TokenResponse>
    fun socialSignup(socialType: SocialType): Single<TokenResponse>
    fun login(loginRequest: LoginRequest): Single<TokenResponse>
    fun reissue(): Single<TokenResponse>
    fun getMyInfo(): Single<StudioUserResponse>
    fun getUserDetail(): Single<UserDetailResponse>
    fun getUserInfo(userId: Long): Single<StudioUserResponse>
    fun getUserAlbums(userId: Long?, pageRequest: PageRequest, placeCondition: PlaceCondition): Single<PageResponse<UserAlbumsResponse>>
}