package com.untilled.roadcapture.data.datasource.dao

import com.untilled.roadcapture.data.datasource.api.dto.user.StudioUserResponse
import com.untilled.roadcapture.data.datasource.api.dto.user.UserDetailResponse

interface LocalUserDao {
    fun saveUser(response: UserDetailResponse)
    fun saveUser(response: StudioUserResponse)
    fun getUser(): Long
    fun clearUser()
}