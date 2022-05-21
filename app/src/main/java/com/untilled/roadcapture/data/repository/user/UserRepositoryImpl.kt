package com.untilled.roadcapture.data.repository.user

import com.untilled.roadcapture.data.datasource.api.RoadCaptureApi
import com.untilled.roadcapture.data.datasource.api.dto.address.PlaceCondition
import com.untilled.roadcapture.data.datasource.api.dto.album.UserAlbumsResponse
import com.untilled.roadcapture.data.datasource.api.dto.common.ErrorCode
import com.untilled.roadcapture.data.datasource.api.dto.common.PageRequest
import com.untilled.roadcapture.data.datasource.api.dto.common.PageResponse
import com.untilled.roadcapture.data.datasource.api.dto.user.*
import com.untilled.roadcapture.data.datasource.dao.LocalOAuthTokenDao
import com.untilled.roadcapture.data.datasource.dao.LocalTokenDao
import com.untilled.roadcapture.utils.retryThreeTimes
import com.untilled.roadcapture.utils.toErrorResponseOrNull
import com.untilled.roadcapture.utils.type.SocialType
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val roadCaptureApi: RoadCaptureApi,
    private val localTokenDao: LocalTokenDao,
    private val localOAuthTokenDao: LocalOAuthTokenDao,
) :
    UserRepository {

    override fun signup(signupRequest: SignupRequest): Single<TokenResponse> {
        return roadCaptureApi.signup(signupRequest)
            .flatMap { response ->
                response.errorBody()?.let {
                    return@flatMap Single.error(IllegalStateException(it.toErrorResponseOrNull()?.message))
                }

                return@flatMap login(
                    LoginRequest(
                        email = signupRequest.email,
                        password = signupRequest.password,
                    )
                )
            }
    }

    override fun socialSignup(socialType: SocialType): Single<TokenResponse> {
        val oauthToken = localOAuthTokenDao.getToken()
        return roadCaptureApi.socialSignup(socialType.name, TokenRequest(oauthToken.accessToken))
            .flatMap { response ->
                response.errorBody()?.let {
                    if (it.toErrorResponseOrNull()?.code != ErrorCode.ALREADY_SIGNEDUP.code) {
                        return@flatMap Single.error(IllegalStateException("Network error"))
                    }
                }

                return@flatMap socialLogin(socialType, oauthToken.accessToken)
            }
    }

    override fun reissue(): Single<TokenResponse> {
        val token = localTokenDao.getToken()
        return roadCaptureApi.reissue(ReissueRequest(token.accessToken, token.refreshToken))
            .flatMap { response ->
                response.errorBody()?.let {
                    return@flatMap Single.error(IllegalStateException(it.toErrorResponseOrNull()?.message))
                }

                response.body()?.let {
                    return@flatMap Single.just(it)
                }
                return@flatMap Single.error(IllegalStateException("Network error"))
            }
    }

    override fun getMyInfo(): Single<StudioUserResponse> =
        roadCaptureApi.getMyStudioUser()


    override fun login(loginRequest: LoginRequest): Single<TokenResponse> =
        roadCaptureApi.login(loginRequest)
            .flatMap { response ->
                response.errorBody()?.let {
                    return@flatMap Single.error(IllegalStateException(it.toErrorResponseOrNull()?.message))
                }

                response.body()?.let {
                    return@flatMap Single.just(it)
                }

                return@flatMap Single.error(IllegalStateException("Network error"))
            }

    private fun socialLogin(socialType: SocialType, accessToken: String): Single<TokenResponse> =
        roadCaptureApi.socialLogin(
            socialType.name,
            TokenRequest(accessToken)
        ).flatMap { response ->
            response.errorBody()?.let {
                return@flatMap when (it.toErrorResponseOrNull()?.code) {
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

    override fun getUserDetail(): Single<UserDetailResponse> =
        roadCaptureApi.getUserDetail()

    override fun getUserInfo(userId: Long): Single<StudioUserResponse> =
        roadCaptureApi.getStudioUser(userId)

    override fun getUserAlbums(
        userId: Long?,
        pageRequest: PageRequest,
        placeCondition: PlaceCondition
    ): Single<PageResponse<UserAlbumsResponse>> =
        roadCaptureApi.getStudioAlbums(
            userId,
            pageRequest.page,
            pageRequest.size,
            placeCondition.address1,
            placeCondition.address2,
            placeCondition.address3
        )

}