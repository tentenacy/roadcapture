package com.untilled.roadcapture.data.repository.user

import com.untilled.roadcapture.data.datasource.api.RoadCaptureApi
import com.untilled.roadcapture.data.datasource.api.dto.common.ErrorCode
import com.untilled.roadcapture.data.datasource.api.dto.user.ReissueRequest
import com.untilled.roadcapture.data.datasource.api.dto.user.TokenRequest
import com.untilled.roadcapture.data.datasource.api.dto.user.TokenResponse
import com.untilled.roadcapture.data.datasource.dao.LocalOAuthTokenDao
import com.untilled.roadcapture.data.datasource.dao.LocalTokenDao
import com.untilled.roadcapture.data.entity.token.NaverOAuthToken
import com.untilled.roadcapture.data.repository.token.dto.TokenArgs
import com.untilled.roadcapture.utils.convertToErrorResponse
import com.untilled.roadcapture.utils.type.SocialType
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val roadCaptureApi: RoadCaptureApi,
    private val localTokenDao: LocalTokenDao,
    private val map: Map<String, @JvmSuppressWildcards LocalOAuthTokenDao>,
    private val retrofit: Retrofit,
) :
    UserRepository {

    override fun socialSignup(socialType: SocialType): Single<TokenResponse> =
        roadCaptureApi.socialSignup(socialType.name, TokenRequest(NaverOAuthToken.accessToken))
            .flatMap { response ->
                if(!response.isSuccessful and (retrofit.convertToErrorResponse(response)?.code != ErrorCode.ALREADY_SIGNEDUP.code)) {
                    return@flatMap Single.error(IllegalStateException("Network error"))
                }

                return@flatMap socialLogin(socialType)
            }


    //TODO: 액세스 토큰이 만료되면 리프레시 토큰으로 토큰 재발행
    override fun reissue(reissueRequest: ReissueRequest): Single<TokenResponse> {
        return Single.create {}
    }

    private fun socialLogin(socialType: SocialType): Single<TokenResponse> =
        roadCaptureApi.socialLogin(
            socialType.name,
            TokenRequest(map[socialType.name]!!.getToken().accessToken)
        ).map { response ->
            localTokenDao.saveToken(
                TokenArgs(
                    grantType = response.grantType,
                    accessToken = response.accessToken,
                    refreshToken = response.refreshToken,
                    accessTokenExpireDate = response.accessTokenExpireDate.toLong(),
                )
            )
            response
        }
}