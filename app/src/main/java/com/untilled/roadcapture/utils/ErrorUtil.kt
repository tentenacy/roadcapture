package com.untilled.roadcapture.utils

import com.untilled.roadcapture.data.datasource.api.dto.common.ErrorResponse
import retrofit2.Response
import retrofit2.Retrofit

fun Retrofit.convertToErrorResponse(response: Response<*>): ErrorResponse? {
    val converter = responseBodyConverter<ErrorResponse>(
        ErrorResponse::class.java, ErrorResponse::class.java.annotations)

    return converter.convert(response.errorBody())
}