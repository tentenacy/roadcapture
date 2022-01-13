package com.untilled.roadcapture.utils.type

sealed class SocialType {
    object GOOGLE : SocialType()
    object FACEBOOK : SocialType()
    object KAKAO : SocialType()
    object NAVER : SocialType()
}