package com.untilled.roadcapture.data.datasource.dao

import com.untilled.roadcapture.data.datasource.api.dto.user.UserResponse
import com.untilled.roadcapture.data.entity.user.User
import javax.inject.Inject

class UserDao @Inject constructor(): LocalUserDao{
    override fun saveUser(id: Int) {
        User.id = id
    }

    override fun getUser(): Int = User.id

    override fun clearUser() {
        User.clear()
    }
}