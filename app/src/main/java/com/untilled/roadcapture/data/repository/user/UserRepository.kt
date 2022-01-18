package com.untilled.roadcapture.data.repository.user

import com.untilled.roadcapture.data.datasource.api.dto.common.PageResponse
import com.untilled.roadcapture.data.datasource.api.dto.user.ReissueRequest
import com.untilled.roadcapture.data.datasource.api.dto.user.TokenResponse
import com.untilled.roadcapture.data.datasource.api.dto.user.Users
import com.untilled.roadcapture.data.entity.User
import com.untilled.roadcapture.utils.type.SocialType
import io.reactivex.rxjava3.core.Single

interface UserRepository {
    fun socialSignup(socialType: SocialType): Single<TokenResponse>
    fun reissue(): Single<TokenResponse>
    fun getUserDetail(): Single<User>
    fun getUserInfo(id: Int): Single<Users>
    fun getUserFollower(id: Int, page: Int?, size: Int?, sort: String?, username: String?): Single<PageResponse<Users>>
    fun getUserFollowing(id: Int, page: Int?, size: Int?, sort: String?, username: String?): Single<PageResponse<Users>>
}