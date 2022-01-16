package com.untilled.roadcapture.data.datasource.dao

import com.untilled.roadcapture.data.entity.token.OAuthToken
import com.untilled.roadcapture.data.repository.token.dto.OAuthTokenArgs
import javax.inject.Inject

class KotPrefOAuthTokenDao @Inject constructor(): LocalOAuthTokenDao {

    override fun saveToken(args: OAuthTokenArgs) {
        OAuthToken.accessToken = args.accessToken
        OAuthToken.refreshToken = args.refreshToken
        OAuthToken.socialType = args.socialType
    }

    override fun getToken(): OAuthTokenArgs {
        return OAuthTokenArgs(
            accessToken = OAuthToken.accessToken,
            refreshToken = OAuthToken.refreshToken,
            socialType = OAuthToken.socialType,
        )
    }
}