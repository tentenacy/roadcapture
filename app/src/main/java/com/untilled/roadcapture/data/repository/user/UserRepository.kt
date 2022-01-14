package com.untilled.roadcapture.data.repository.user

import com.untilled.roadcapture.data.api.dto.user.SocialLoginResponse
import com.untilled.roadcapture.utils.type.SocialType
import io.reactivex.rxjava3.core.Single
import okhttp3.Response
import okhttp3.ResponseBody

interface UserRepository {
    fun socialSignup(socialType: SocialType): Single<SocialLoginResponse>
}