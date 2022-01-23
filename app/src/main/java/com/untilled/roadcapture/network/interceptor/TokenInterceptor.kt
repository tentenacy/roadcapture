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
import okhttp3.Response

class TokenInterceptor(
    private val localTokenDao: LocalTokenDao,
    private val gson: Gson,
) : Interceptor, Subject<TokenExpirationObserver>() {

    private var accessTokenErrorOccurred = false
    private var refreshTokenErrorOccurred = false

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("X-AUTH-TOKEN", localTokenDao.getToken().accessToken)
            .build()
        val response = chain.proceed(request)

        if (!response.isSuccessful) {
            response.peekBody(2048).toErrorResponseOrNull(gson)?.apply {
                when (code) {
                    ErrorCode.ACCESS_TOKEN_ERROR.code -> {
                        if(!accessTokenErrorOccurred) {
                            notifyTokenExpired()
                            accessTokenErrorOccurred = true
                        }
                    }
                    ErrorCode.REFRESH_TOKEN_ERROR.code -> {
                        if(!refreshTokenErrorOccurred) {
                            notifyRefreshTokenExpired()
                            refreshTokenErrorOccurred = true
                        }
                    }
                }
            }
        } else {
            response.peekBody(2048).toTokenResponseOrNull(gson)?.apply {
                localTokenDao.saveToken(
                    TokenArgs(
                        grantType = grantType,
                        accessToken = accessToken,
                        refreshToken = refreshToken,
                        accessTokenExpireDate = accessTokenExpireDate.toLong(),
                    )
                )
                accessTokenErrorOccurred = false
                refreshTokenErrorOccurred = false
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