package com.untilled.roadcapture.data.repository.user

import com.untilled.roadcapture.data.datasource.api.dto.user.ReissueRequest
import com.untilled.roadcapture.data.datasource.api.dto.user.TokenResponse
import com.untilled.roadcapture.data.datasource.api.dto.user.UserFollowResponse
import com.untilled.roadcapture.data.datasource.api.dto.user.Users
import com.untilled.roadcapture.data.entity.User
import com.untilled.roadcapture.utils.type.SocialType
import io.reactivex.rxjava3.core.Single

interface UserRepository {
    fun socialSignup(socialType: SocialType): Single<TokenResponse>
    fun reissue(reissueRequest: ReissueRequest): Single<TokenResponse>
    fun getUserDetail(token: String): Single<User>
    fun getUserInfo(id: Int, token: String): Single<Users>
    fun getUserFollower(id: Int, token: String, page: Int?, size: Int?, sort: String?, username: String?): Single<UserFollowResponse>
    fun getUserFollowing(id: Int, token: String, page: Int?, size: Int?, sort: String?, username: String?): Single<UserFollowResponse>
}