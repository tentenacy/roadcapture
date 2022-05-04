package com.untilled.roadcapture.utils

import com.untilled.roadcapture.utils.type.SocialType
import java.text.DecimalFormat

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

fun Int.toZeroZeroFormat(): String = DecimalFormat("00").format(this)