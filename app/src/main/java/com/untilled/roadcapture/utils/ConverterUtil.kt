package com.untilled.roadcapture.utils

import com.untilled.roadcapture.data.datasource.api.dto.common.ErrorResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit

fun Retrofit.convertToErrorResponse(responseBody: ResponseBody): ErrorResponse? =
    responseBodyConverter<ErrorResponse>(
        ErrorResponse::class.java, ErrorResponse::class.java.annotations
    ).convert(responseBody)