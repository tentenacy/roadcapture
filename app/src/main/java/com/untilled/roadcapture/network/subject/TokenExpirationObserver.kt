package com.untilled.roadcapture.network.subject

interface TokenExpirationObserver {
    fun onTokenExpired()
}