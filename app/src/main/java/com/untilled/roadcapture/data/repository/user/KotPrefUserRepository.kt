package com.untilled.roadcapture.data.repository.user

import com.untilled.roadcapture.data.datasource.api.dto.user.UserResponse
import com.untilled.roadcapture.data.datasource.dao.LocalOAuthTokenDao
import com.untilled.roadcapture.data.datasource.dao.LocalTokenDao
import com.untilled.roadcapture.data.datasource.dao.LocalUserDao
import com.untilled.roadcapture.data.repository.token.LocalTokenRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KotPrefUserRepository @Inject constructor(private val localUserDao: LocalUserDao):
    LocalUserRepository {
    override fun saveUser(id: Long) {
        localUserDao.saveUser(id)
    }

    override fun getUser(): Long = localUserDao.getUser()

    override fun clearUser() {
        localUserDao.clearUser()
    }

}