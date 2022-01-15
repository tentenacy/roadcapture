package com.untilled.roadcapture.data.repository.token

import com.untilled.roadcapture.data.datasource.dao.LocalOAuthTokenDao
import com.untilled.roadcapture.data.datasource.dao.LocalTokenDao
import com.untilled.roadcapture.data.repository.token.dto.OAuthTokenArgs
import com.untilled.roadcapture.data.repository.token.dto.TokenArgs
import com.untilled.roadcapture.utils.type.SocialType
import javax.inject.Inject

class KotPrefTokenRepository @Inject constructor(private val localOAuthTokenDao: LocalOAuthTokenDao, private val localTokenDao: LocalTokenDao): LocalTokenRepository {

    override fun saveOAuthToken(socialType: SocialType, args: OAuthTokenArgs) {
        localOAuthTokenDao.saveToken(args)
    }

    override fun saveToken(socialType: SocialType, args: TokenArgs) {
        localTokenDao.saveToken(args)
    }
}
