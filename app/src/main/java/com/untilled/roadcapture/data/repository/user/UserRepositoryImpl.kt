package com.untilled.roadcapture.data.repository.user

import com.google.gson.Gson
import com.untilled.roadcapture.data.datasource.api.RoadCaptureApi
import com.untilled.roadcapture.data.datasource.api.dto.common.ErrorCode
import com.untilled.roadcapture.data.datasource.api.dto.common.PageRequest
import com.untilled.roadcapture.data.datasource.api.dto.common.PageResponse
import com.untilled.roadcapture.data.datasource.api.dto.user.*
import com.untilled.roadcapture.data.datasource.dao.LocalOAuthTokenDao
import com.untilled.roadcapture.data.datasource.dao.LocalTokenDao
import com.untilled.roadcapture.data.datasource.dao.LocalUserDao
import com.untilled.roadcapture.utils.retryThreeTimes
import com.untilled.roadcapture.utils.toErrorResponse
import com.untilled.roadcapture.utils.type.SocialType
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val roadCaptureApi: RoadCaptureApi,
    private val localTokenDao: LocalTokenDao,
    private val localOAuthTokenDao: LocalOAuthTokenDao,
    private val localUserDao: LocalUserDao,
    private val gson: Gson,
) :
    UserRepository {

    override fun socialSignup(socialType: SocialType): Single<TokenResponse> {
        val oauthToken = localOAuthTokenDao.getToken()
        return roadCaptureApi.socialSignup(socialType.name, TokenRequest(oauthToken.accessToken))
            .flatMap { response ->
                response.errorBody()?.let {
                    if (it.toErrorResponse(gson)?.code != ErrorCode.ALREADY_SIGNEDUP.code) {
                        return@flatMap Single.error(IllegalStateException("Network error"))
                    }
                }

                return@flatMap socialLogin(socialType, oauthToken.accessToken)
            }
            .retryThreeTimes()
    }

    override fun reissue(): Single<TokenResponse> {
        val token = localTokenDao.getToken()
        return roadCaptureApi.reissue(ReissueRequest(token.accessToken, token.refreshToken))
            .flatMap { response ->
                response.body()?.let {
                    return@flatMap Single.just(it)
                }
                return@flatMap Single.error(IllegalStateException("Network error"))
            }
            .retryThreeTimes()
    }

    private fun socialLogin(socialType: SocialType, accessToken: String): Single<TokenResponse> =
        roadCaptureApi.socialLogin(
            socialType.name,
            TokenRequest(accessToken)
        ).flatMap { response ->
            response.errorBody()?.let {
                return@flatMap when (it.toErrorResponse(gson)?.code) {
                    ErrorCode.USER_NOT_FOUND.code -> {
                        Single.error(IllegalStateException(ErrorCode.USER_NOT_FOUND.message))
                    }
                    else -> Single.error(IllegalStateException("Network error"))
                }
            }

            response.body()?.let {
                return@flatMap Single.just(it)
            }

            return@flatMap Single.error(IllegalStateException("Network error"))
        }
            .retryThreeTimes()

    override fun getUserDetail(): Single<UserResponse> =
        roadCaptureApi.getUserDetail()

    override fun getUserInfo(id: Int): Single<UsersResponse> =
        roadCaptureApi.getUserInfo(id)


    override fun getUserFollower(
        followingsCondition: FollowingsCondition, pageRequest: PageRequest
    ): Single<PageResponse<UsersResponse>> =
        roadCaptureApi.getUserFollower(followingsCondition.userId,pageRequest.page,pageRequest.size,pageRequest.sort,followingsCondition.username)

    override fun getUserFollowing(
        followingsCondition: FollowingsCondition, pageRequest: PageRequest
    ): Single<PageResponse<UsersResponse>> =
        roadCaptureApi.getUserFollowing(followingsCondition.userId,pageRequest.page,pageRequest.size,pageRequest.sort,followingsCondition.username)
}