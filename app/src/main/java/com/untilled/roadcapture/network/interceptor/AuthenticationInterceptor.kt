package com.untilled.roadcapture.network.interceptor

import com.untilled.roadcapture.data.datasource.dao.LocalTokenDao
import com.untilled.roadcapture.network.subject.AuthenticationSubject
import com.untilled.roadcapture.network.subject.TokenExpirationObserver
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthenticationInterceptor @Inject constructor(
    private val localTokenDao: LocalTokenDao,
) : Interceptor, AuthenticationSubject<TokenExpirationObserver>() {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().addHeader("X-AUTH-TOKEN", localTokenDao.getToken().accessToken).build()
        val response = chain.proceed(request)

        //소셜 액세스 토큰이 유효하지 않으면 로그아웃

        when (response.code) {
            401 -> {
                //Show UnauthorizedError Message
                notifyTokenExpired()
            }
        }
        return response
    }

    private fun notifyTokenExpired() {
        observers.forEach(TokenExpirationObserver::onTokenExpired)
    }
}