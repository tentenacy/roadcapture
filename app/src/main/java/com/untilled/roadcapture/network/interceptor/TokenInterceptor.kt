package com.untilled.roadcapture.network.interceptor

import com.google.gson.Gson
import com.untilled.roadcapture.data.datasource.api.dto.common.ErrorCode
import com.untilled.roadcapture.data.datasource.api.dto.common.ErrorResponse
import com.untilled.roadcapture.data.datasource.dao.LocalTokenDao
import com.untilled.roadcapture.network.subject.Subject
import com.untilled.roadcapture.network.observer.TokenExpirationObserver
import com.untilled.roadcapture.utils.toErrorResponse
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor(
    private val localTokenDao: LocalTokenDao,
    private val gson: Gson,
) : Interceptor, Subject<TokenExpirationObserver>() {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("X-AUTH-TOKEN", localTokenDao.getToken().accessToken)
            .build()
        val response = chain.proceed(request)

        if (!response.isSuccessful) {
            val errorResponse: ErrorResponse? = response.peekBody(2048).toErrorResponse(gson)

            when (errorResponse?.code) {
                ErrorCode.ACCESS_TOKEN_ERROR.code -> {
                    if(++count == 1)
                        notifyTokenExpired()
                }
                ErrorCode.REFRESH_TOKEN_ERROR.code -> {
                    if(++count == 1)
                        notifyRefreshTokenExpired()
                }
            }
        }

        return response
    }

    private fun notifyTokenExpired() {
        observers.forEach(TokenExpirationObserver::onTokenExpired)
    }

    private fun notifyRefreshTokenExpired() {
        observers.forEach(TokenExpirationObserver::onRefreshTokenExpired)
    }
}