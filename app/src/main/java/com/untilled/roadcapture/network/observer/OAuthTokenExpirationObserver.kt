package com.untilled.roadcapture.network.observer

interface OAuthTokenExpirationObserver {
    fun onOAuthTokenExpired()
}