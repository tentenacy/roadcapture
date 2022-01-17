package com.untilled.roadcapture.data.repository.user

import com.google.gson.Gson
import com.untilled.roadcapture.data.datasource.api.RoadCaptureApi
import com.untilled.roadcapture.data.datasource.api.dto.common.ErrorCode
import com.untilled.roadcapture.data.datasource.api.dto.common.PageResponse
import com.untilled.roadcapture.data.datasource.api.dto.user.*
import com.untilled.roadcapture.data.datasource.dao.LocalOAuthTokenDao
import com.untilled.roadcapture.data.datasource.dao.LocalTokenDao
import com.untilled.roadcapture.data.datasource.dao.LocalUserDao
import com.untilled.roadcapture.data.entity.User
import com.untilled.roadcapture.data.repository.token.dto.TokenArgs
import com.untilled.roadcapture.utils.toErrorResponse
import com.untilled.roadcapture.utils.type.SocialType
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
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
    }

    override fun reissue(reissueRequest: ReissueRequest): Single<TokenResponse> {
        return roadCaptureApi.reissue(reissueRequest)
            .map { response ->
                localTokenDao.saveToken(
                    TokenArgs(
                        grantType = response.body()!!.grantType,
                        accessToken = response.body()!!.accessToken,
                        refreshToken = response.body()!!.refreshToken,
                        accessTokenExpireDate = response.body()!!.accessTokenExpireDate.toLong(),
                    )
                )
                response.body()!!
            }
    }

    private fun socialLogin(socialType: SocialType, accessToken: String): Single<TokenResponse> =
        roadCaptureApi.socialLogin(
            socialType.name,
            TokenRequest(accessToken)
        ).flatMap { response ->
            response.errorBody()?.let {
                return@flatMap when(it.toErrorResponse(gson)?.code) {
                    ErrorCode.USER_NOT_FOUND.code -> {
                        Single.error(IllegalStateException(ErrorCode.USER_NOT_FOUND.message))
                    }
                    else -> Single.error(IllegalStateException("Network error"))
                }
            }

            response.body()?.let {
                localTokenDao.saveToken(
                    TokenArgs(
                        grantType = it.grantType,
                        accessToken = it.accessToken,
                        refreshToken = it.refreshToken,
                        accessTokenExpireDate = it.accessTokenExpireDate.toLong(),
                    )
                )

                return@flatMap Single.just(it)
            }

            return@flatMap Single.error(IllegalStateException("Network error"))
        }

    override fun getUserDetail(): Single<User> =
        roadCaptureApi.getUserDetail()
            .map { user ->
                localUserDao.saveUserId(user.id)
                user
            }

    override fun getUserInfo(id: Int): Single<Users> =
        roadCaptureApi.getUserInfo(id)


    override fun getUserFollower(id: Int, page: Int?, size: Int?, sort: String?,username: String?): Single<PageResponse<Users>> =
        roadCaptureApi.getUserFollower(id, page, size, sort, username)

    override fun getUserFollowing(id: Int, page: Int?, size: Int?, sort: String?, username: String?): Single<PageResponse<Users>> =
        roadCaptureApi.getUserFollowing(id, page, size, sort, username)
}