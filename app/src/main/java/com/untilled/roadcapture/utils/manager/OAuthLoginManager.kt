package com.untilled.roadcapture.utils.manager

interface OAuthLoginManager {
    fun logout()
    fun reissue()
    fun withdrawal()
}