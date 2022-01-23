package com.untilled.roadcapture.data.repository.user

import com.google.gson.Gson
import com.untilled.roadcapture.data.datasource.api.RoadCaptureApi
import com.untilled.roadcapture.data.datasource.api.dto.common.ErrorCode
import com.untilled.roadcapture.data.datasource.api.dto.common.PageRequest
import com.untilled.roadcapture.data.datasource.api.dto.common.PageResponse
import com.untilled.roadcapture.data.datasource.api.dto.address.PlaceCondition
import com.untilled.roadcapture.data.datasource.api.dto.album.UserAlbumsResponse
import com.untilled.roadcapture.data.datasource.api.dto.user.*
import com.untilled.roadcapture.data.datasource.dao.LocalOAuthTokenDao
import com.untilled.roadcapture.data.datasource.dao.LocalTokenDao
import com.untilled.roadcapture.utils.retryThreeTimes
import com.untilled.roadcapture.utils.toErrorResponseOrNull
import com.untilled.roadcapture.utils.type.SocialType
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.Flowables
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val roadCaptureApi: RoadCaptureApi,
    private val localTokenDao: LocalTokenDao,
    private val localOAuthTokenDao: LocalOAuthTokenDao,
    private val gson: Gson,
) :
    UserRepository {

    override fun signup(signupRequest: SignupRequest): Single<TokenResponse> {
        return roadCaptureApi.signup(signupRequest)
            .flatMap { response ->
                response.errorBody()?.let {
                    return@flatMap Single.error(IllegalStateException(it.toErrorResponseOrNull(gson)?.message))
                }

                return@flatMap login(LoginRequest(
                    email = signupRequest.email,
                    password = signupRequest.password,
                ))
            }
            .retryThreeTimes()
    }

    override fun socialSignup(socialType: SocialType): Single<TokenResponse> {
        val oauthToken = localOAuthTokenDao.getToken()
        return roadCaptureApi.socialSignup(socialType.name, TokenRequest(oauthToken.accessToken))
            .flatMap { response ->
                response.errorBody()?.let {
                    if (it.toErrorResponseOrNull(gson)?.code != ErrorCode.ALREADY_SIGNEDUP.code) {
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
                response.errorBody()?.let {
                    return@flatMap Single.error(IllegalStateException(it.toErrorResponseOrNull(gson)?.message))
                }

                response.body()?.let {
                    return@flatMap Single.just(it)
                }
                return@flatMap Single.error(IllegalStateException("Network error"))
            }
    }

    override fun getMyInfo(): Single<UsersResponse> = roadCaptureApi.getMyInfo()


    override fun login(loginRequest: LoginRequest): Single<TokenResponse> =
        roadCaptureApi.login(loginRequest)
            .flatMap { response ->
                response.errorBody()?.let {
                    return@flatMap Single.error(IllegalStateException(it.toErrorResponseOrNull(gson)?.message))
                }

                response.body()?.let {
                    return@flatMap Single.just(it)
                }

                return@flatMap Single.error(IllegalStateException("Network error"))
            }
            .retryThreeTimes()

    private fun socialLogin(socialType: SocialType, accessToken: String): Single<TokenResponse> =
        roadCaptureApi.socialLogin(
            socialType.name,
            TokenRequest(accessToken)
        ).flatMap { response ->
            response.errorBody()?.let {
                return@flatMap when (it.toErrorResponseOrNull(gson)?.code) {
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
            .retryThreeTimes()

    override fun getUserInfo(userId: Long): Single<UsersResponse> =
        roadCaptureApi.getUserInfo(userId)
            .retryThreeTimes()

    override fun getUserAlbums(userId: Long?,pageRequest: PageRequest, placeCondition: PlaceCondition): Single<PageResponse<UserAlbumsResponse>> =
        roadCaptureApi.getStudioAlbums(userId, pageRequest.page,pageRequest.size,placeCondition.address1,placeCondition.address2,placeCondition.address3)

    override fun getUserFollower(
        userId: Long, followingsCondition: FollowingsCondition, pageRequest: PageRequest
    ): Single<PageResponse<UsersResponse>> =
        roadCaptureApi.getUserFollowers(userId,pageRequest.page,pageRequest.size,pageRequest.sort,followingsCondition.username)
            .retryThreeTimes()

    override fun getUserFollowing(
        userId: Long, followingsCondition: FollowingsCondition, pageRequest: PageRequest
    ): Single<PageResponse<UsersResponse>> =
        roadCaptureApi.getUserFollowings(userId,pageRequest.page,pageRequest.size,pageRequest.sort,followingsCondition.username)
            .retryThreeTimes()
}