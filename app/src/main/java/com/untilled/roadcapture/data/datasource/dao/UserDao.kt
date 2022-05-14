package com.untilled.roadcapture.data.datasource.dao

import com.untilled.roadcapture.data.datasource.api.dto.user.StudioUserResponse
import com.untilled.roadcapture.data.datasource.api.dto.user.UserDetailResponse
import com.untilled.roadcapture.data.datasource.sharedpref.User
import javax.inject.Inject

class UserDao @Inject constructor(): LocalUserDao{
    override fun saveUser(response: UserDetailResponse) {
        User.save(response)
    }

    override fun saveUser(response: StudioUserResponse) {
        User.save(response)
    }

    override fun getUser(): Long = User.id

    override fun clearUser() {
        User.clear()
    }
}