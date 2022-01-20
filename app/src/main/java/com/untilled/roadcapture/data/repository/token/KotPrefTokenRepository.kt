package com.untilled.roadcapture.data.repository.token

import com.untilled.roadcapture.data.datasource.dao.LocalOAuthTokenDao
import com.untilled.roadcapture.data.datasource.dao.LocalTokenDao
import com.untilled.roadcapture.data.repository.token.dto.OAuthTokenArgs
import com.untilled.roadcapture.data.repository.token.dto.TokenArgs
import com.untilled.roadcapture.utils.type.SocialType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KotPrefTokenRepository @Inject constructor(private val localOAuthTokenDao: LocalOAuthTokenDao, private val localTokenDao: LocalTokenDao): LocalTokenRepository {

    override fun saveOAuthToken(args: OAuthTokenArgs) {
        localOAuthTokenDao.saveToken(args)
    }

    override fun saveToken(args: TokenArgs) {
        localTokenDao.saveToken(args)
    }

    override fun getOAuthToken(): OAuthTokenArgs = localOAuthTokenDao.getToken()

    override fun getToken(): TokenArgs = localTokenDao.getToken()

    override fun clearToken() {
        localTokenDao.clearToken()
        localOAuthTokenDao.clearToken()
    }
}
