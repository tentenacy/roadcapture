package com.untilled.roadcapture.data.repository.user

import com.untilled.roadcapture.data.datasource.api.RoadCaptureApi
import com.untilled.roadcapture.data.datasource.api.dto.common.ErrorCode
import com.untilled.roadcapture.data.datasource.api.dto.user.SocialLoginResponse
import com.untilled.roadcapture.data.datasource.api.dto.user.SocialRequest
import com.untilled.roadcapture.data.entity.token.NaverOAuthToken
import com.untilled.roadcapture.utils.convertToErrorResponse
import com.untilled.roadcapture.utils.type.SocialType
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val roadCaptureApi: RoadCaptureApi,
    private val retrofit: Retrofit
) :
    UserRepository {

    override fun socialSignup(socialType: SocialType): Single<SocialLoginResponse> =
        roadCaptureApi.socialSignup(socialType.name, SocialRequest(NaverOAuthToken.accessToken))
            .flatMap { response ->
                return@flatMap if (!response.isSuccessful and
                    (retrofit.convertToErrorResponse(response)?.code != ErrorCode.ALREADY_SIGNEDUP.code)) {
                    Single.error(IllegalStateException("Network error"))
                } else {
                    socialLogin(socialType)
                }
            }

    private fun socialLogin(socialType: SocialType): Single<SocialLoginResponse> =
        when (socialType) {
            SocialType.KAKAO -> ({
                Any()
            }) as Single<SocialLoginResponse>
            SocialType.GOOGLE -> ({
                Any()
            }) as Single<SocialLoginResponse>
            SocialType.NAVER -> {
                roadCaptureApi.socialLogin(
                    socialType.name,
                    SocialRequest(NaverOAuthToken.accessToken)
                )
            }
            SocialType.FACEBOOK -> ({
                Any()
            }) as Single<SocialLoginResponse>
        }
}