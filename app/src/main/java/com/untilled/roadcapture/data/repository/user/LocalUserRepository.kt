package com.untilled.roadcapture.data.repository.user

interface LocalUserRepository {
    fun saveUser(id: Long)
    fun getUser(): Long

    fun clearUser()
}