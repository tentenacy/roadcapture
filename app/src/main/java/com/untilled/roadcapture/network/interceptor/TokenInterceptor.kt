package com.untilled.roadcapture.network.interceptor

import com.google.gson.Gson
import com.untilled.roadcapture.data.datasource.api.dto.common.ErrorCode
import com.untilled.roadcapture.data.datasource.api.dto.common.ErrorResponse
import com.untilled.roadcapture.data.datasource.dao.LocalTokenDao
import com.untilled.roadcapture.data.repository.token.dto.TokenArgs
import com.untilled.roadcapture.network.subject.Subject
import com.untilled.roadcapture.network.observer.TokenExpirationObserver
import com.untilled.roadcapture.utils.toErrorResponseOrNull
import com.untilled.roadcapture.utils.toTokenResponseOrNull
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response

class TokenInterceptor(
    private val localTokenDao: LocalTokenDao,
) : Interceptor, Subject<TokenExpirationObserver>() {

    private var accessTokenErrorOccurred = false

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("X-AUTH-TOKEN", localTokenDao.getToken().accessToken)
            .build()
        var response = chain.proceed(request)

        var tryCount: Byte = 0

        if (!response.isSuccessful) {
            response.peekBody(2048).toErrorResponseOrNull()?.apply {
                when (code) {
                    ErrorCode.ACCESS_TOKEN_ERROR.code -> {
                        if(!accessTokenErrorOccurred) {
                            notifyTokenExpired()
                            accessTokenErrorOccurred = true
                        }
                        while(tryCount++ < 3 && !response.isSuccessful && response.peekBody(2048).toErrorResponseOrNull()?.code == ErrorCode.ACCESS_TOKEN_ERROR.code) {
                            Thread.sleep((1000 * tryCount).toLong())
                            response = chain.proceed(request)
                        }
                    }
                    ErrorCode.REFRESH_TOKEN_ERROR.code -> {
                        notifyRefreshTokenExpired()
                    }
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

    fun resetTokenErrorOccurred() {
        accessTokenErrorOccurred = false
    }
}