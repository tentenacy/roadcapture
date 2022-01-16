package com.untilled.roadcapture.utils

import com.untilled.roadcapture.utils.type.SocialType

fun String.getSocialType(): SocialType? = when(this) {
    "naver" -> {
        SocialType.NAVER
    }
    "kakao" -> {
        SocialType.KAKAO
    }
    "google" -> {
        SocialType.GOOGLE
    }
    "facebook" -> {
        SocialType.FACEBOOK
    }
    else -> null
}