package com.untilled.roadcapture.data.datasource.dao

import com.untilled.roadcapture.data.repository.token.dto.OAuthTokenArgs
import com.untilled.roadcapture.data.repository.token.dto.TokenArgs

interface LocalTokenDao {
    fun saveToken(tokenArgs: TokenArgs)
}