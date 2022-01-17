package com.untilled.roadcapture.network.interceptor

import com.google.gson.Gson
import com.orhanobut.logger.Logger
import com.untilled.roadcapture.data.datasource.api.dto.common.ErrorCode
import com.untilled.roadcapture.data.datasource.api.dto.common.ErrorResponse
import com.untilled.roadcapture.data.datasource.dao.LocalTokenDao
import com.untilled.roadcapture.network.subject.AuthenticationSubject
import com.untilled.roadcapture.network.subject.TokenExpirationObserver
import com.untilled.roadcapture.utils.toErrorResponse
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthenticationInterceptor @Inject constructor(
    private val localTokenDao: LocalTokenDao,
    private val gson: Gson,
) : Interceptor, AuthenticationSubject<TokenExpirationObserver>() {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("X-AUTH-TOKEN", localTokenDao.getToken().accessToken).build()
        val response = chain.proceed(request)

        //소셜 액세스 토큰이 유효하지 않으면 로그아웃
        if (!response.isSuccessful) {
            val errorResponse: ErrorResponse? = response.peekBody(2048).toErrorResponse(gson)

            when (errorResponse?.code) {
                ErrorCode.ACCESS_TOKEN_ERROR.code -> {
                    notifyTokenExpired()
                }
                ErrorCode.REFRESH_TOKEN_ERROR.code -> {

                }
            }
        }

        return response
    }

    private fun notifyTokenExpired() {
        observers.forEach(TokenExpirationObserver::onTokenExpired)
    }
}