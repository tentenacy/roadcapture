package com.untilled.roadcapture.utils

import com.google.gson.Gson
import com.untilled.roadcapture.data.datasource.api.dto.common.ErrorResponse
import com.untilled.roadcapture.data.datasource.api.dto.user.TokenResponse
import okhttp3.ResponseBody
import java.lang.Exception

fun ResponseBody.toErrorResponseOrNull(gson: Gson): ErrorResponse? {
    return try {
        gson.fromJson(charStream(), ErrorResponse::class.java).run { if(isNotEmpty()) this else null }
    } catch (e: Exception) {
        null
    }
}

fun ResponseBody.toTokenResponseOrNull(gson: Gson): TokenResponse? {
    return try {
        gson.fromJson(charStream(), TokenResponse::class.java).run { if(isNotEmpty()) this else null }
    } catch (e: Exception) {
        null
    }
}