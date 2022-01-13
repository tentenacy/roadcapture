package com.untilled.roadcapture.data.repository.user

import com.untilled.roadcapture.utils.type.SocialType
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(): UserRepository {
    override fun socialLogin(socialType: SocialType) {
        when(socialType) {
            is SocialType.KAKAO -> {

            }
            is SocialType.GOOGLE -> {

            }
            is SocialType.NAVER -> {

            }
            is SocialType.FACEBOOK -> {

            }
        }
    }
}