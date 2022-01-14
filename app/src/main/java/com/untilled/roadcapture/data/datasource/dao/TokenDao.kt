package com.untilled.roadcapture.data.datasource.dao

import com.untilled.roadcapture.data.entity.token.Token
import com.untilled.roadcapture.data.repository.token.dto.TokenArgs
import javax.inject.Inject

class TokenDao @Inject constructor(): LocalTokenDao {

    override fun saveToken(args: TokenArgs) {
        Token.grantType = args.grantType
        Token.accessToken = args.accessToken
        Token.refreshToken = args.refreshToken
        Token.accessTokenExpireDate = args.accessTokenExpireDate
    }
}