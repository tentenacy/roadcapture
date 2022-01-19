package com.untilled.roadcapture.network.interceptor

import com.untilled.roadcapture.data.datasource.dao.LocalOAuthTokenDao
import com.untilled.roadcapture.network.subject.OAuthLoginManagerSubject
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class OAuthTokenInterceptor @Inject constructor(
    private val localOAuthTokenDao: LocalOAuthTokenDao,
    private val oauthLoginManagerMap: Map<String, @JvmSuppressWildcards OAuthLoginManagerSubject>,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        localOAuthTokenDao.getToken().whenHasOAuthToken {
            oauthLoginManagerMap[it.name]?.validateToken()
        }

        return response
    }
}