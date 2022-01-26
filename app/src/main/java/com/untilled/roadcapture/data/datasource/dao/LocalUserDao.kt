package com.untilled.roadcapture.data.datasource.dao

interface LocalUserDao {
    fun saveUser(id: Long)
    fun getUser(): Long
    fun clearUser()
}