package com.untilled.roadcapture.data.datasource.dao

import com.untilled.roadcapture.data.datasource.api.dto.user.UserResponse

interface LocalUserDao {
    fun saveUser(id: Long)
    fun getUser(): Long
    fun clearUser()
}