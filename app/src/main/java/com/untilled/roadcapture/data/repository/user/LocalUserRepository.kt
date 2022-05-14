package com.untilled.roadcapture.data.repository.user

import com.untilled.roadcapture.data.datasource.api.dto.user.StudioUserResponse
import com.untilled.roadcapture.data.datasource.api.dto.user.UserDetailResponse

interface LocalUserRepository {
    fun saveUser(response: UserDetailResponse)
    fun saveUser(response: StudioUserResponse)
    fun getUser(): Long
    fun clearUser()
}