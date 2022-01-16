package com.untilled.roadcapture.data.datasource.dao

import com.untilled.roadcapture.data.entity.user.User
import javax.inject.Inject

class UserDao @Inject constructor(): LocalUserDao{
    override fun saveUserId(id: Int) {
        User.userId = id
    }
}