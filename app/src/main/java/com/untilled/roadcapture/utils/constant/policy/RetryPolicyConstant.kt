package com.untilled.roadcapture.utils.constant.policy

import com.orhanobut.logger.Logger
import com.untilled.roadcapture.data.datasource.api.dto.common.ErrorCode
import com.untilled.roadcapture.utils.toErrorResponseOrNull
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException


typealias RETRY_PREDICATE = (Throwable) -> Boolean

object RetryPolicyConstant {

    const val MAX_RETRIES = 3L
    const val DEFAULT_INTERVAL = 1L

    val TIMEOUT: RETRY_PREDICATE = { it is SocketTimeoutException }
    val NETWORK: RETRY_PREDICATE = { it is IOException }
    val SERVICE_UNAVAILABLE: RETRY_PREDICATE = { it is HttpException && it.code() == 503 }
    val ACCESS_TOKEN_EXPIRED: RETRY_PREDICATE = {
        it is HttpException && it.response()?.errorBody()?.toErrorResponseOrNull()
            ?.run {
                Logger.e("code: $code")
                code == ErrorCode.ACCESS_TOKEN_ERROR.code
            } ?: false
    }
}