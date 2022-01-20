package com.untilled.roadcapture.data.datasource.dao

import com.untilled.roadcapture.data.datasource.sharedpref.User
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