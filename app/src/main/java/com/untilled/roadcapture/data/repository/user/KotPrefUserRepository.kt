package com.untilled.roadcapture.data.repository.user

import com.untilled.roadcapture.data.datasource.api.dto.user.StudioUserResponse
import com.untilled.roadcapture.data.datasource.api.dto.user.UserDetailResponse
import com.untilled.roadcapture.data.datasource.dao.LocalUserDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KotPrefUserRepository @Inject constructor(private val localUserDao: LocalUserDao):
    LocalUserRepository {
    override fun saveUser(response: UserDetailResponse) {
        localUserDao.saveUser(response)
    }

    override fun saveUser(response: StudioUserResponse) {
        localUserDao.saveUser(response)
    }

    override fun getUser(): Long = localUserDao.getUser()

    override fun clearUser() {
        localUserDao.clearUser()
    }

}