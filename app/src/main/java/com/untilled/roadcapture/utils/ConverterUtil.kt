package com.untilled.roadcapture.utils

import com.google.gson.Gson
import com.untilled.roadcapture.data.datasource.api.dto.common.ErrorResponse
import okhttp3.ResponseBody

fun ResponseBody.toErrorResponse(gson: Gson): ErrorResponse? = gson.fromJson(charStream(), ErrorResponse::class.java)