package com.untilled.roadcapture.utils.converter

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.format.DateTimeFormatter

import java.time.LocalDateTime

import com.google.gson.JsonParseException

import com.google.gson.JsonDeserializationContext

import com.google.gson.JsonElement

import com.google.gson.JsonPrimitive

import com.google.gson.JsonSerializationContext

import com.google.gson.JsonDeserializer

import com.google.gson.JsonSerializer
import java.lang.reflect.Type


class GsonLocalDateTimeAdapter : JsonSerializer<LocalDateTime?>,
    JsonDeserializer<LocalDateTime?> {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun serialize(
        localDateTime: LocalDateTime?,
        srcType: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(localDateTime))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalDateTime {
        return LocalDateTime.parse(json.asString, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }
}