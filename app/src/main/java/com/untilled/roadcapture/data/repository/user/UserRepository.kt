package com.untilled.roadcapture.data.repository.user

import com.untilled.roadcapture.utils.type.SocialType

interface UserRepository {
    fun socialLogin(socialType: SocialType)
}