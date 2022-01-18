package com.untilled.roadcapture.network.observer

interface OAuthRefreshTokenExpirationObserver {
    fun onRefreshTokenExpired()
}