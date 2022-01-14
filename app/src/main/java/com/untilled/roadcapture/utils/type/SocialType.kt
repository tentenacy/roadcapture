package com.untilled.roadcapture.utils.type

sealed class SocialType {
    object KAKAO : SocialType()
    object GOOGLE : SocialType()
    object NAVER : SocialType()
    object FACEBOOK : SocialType()

    val name: String get() = when(this) {
        is KAKAO -> "kakao"
        is GOOGLE -> "google"
        is NAVER -> "naver"
        is FACEBOOK -> "facebook"
    }
}