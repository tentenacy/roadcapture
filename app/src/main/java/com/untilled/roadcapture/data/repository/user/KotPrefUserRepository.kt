package com.untilled.roadcapture.data.repository.user

import com.untilled.roadcapture.data.datasource.api.dto.user.UserResponse
import com.untilled.roadcapture.data.datasource.dao.LocalOAuthTokenDao
import com.untilled.roadcapture.data.datasource.dao.LocalTokenDao
import com.untilled.roadcapture.data.datasource.dao.LocalUserDao
import com.untilled.roadcapture.data.repository.token.LocalTokenRepository
import javax.inject.Inject

class KotPrefUserRepository @Inject constructor(private val localUserDao: LocalUserDao):
    LocalUserRepository {
    override fun saveUser(id: Int) {
        localUserDao.saveUser(id)
    }

    override fun getUser(): Int = localUserDao.getUser()

    override fun clearUser() {
        localUserDao.clearUser()
    }

}