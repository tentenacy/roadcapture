package com.untilled.roadcapture.utils.converter

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class RoomConverters {
    @TypeConverter
    fun toLocalDateTime(datetime: String): LocalDateTime {
        return LocalDateTime.parse(datetime, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }

    @TypeConverter
    fun fromLocalDateTime(datetime: LocalDateTime): String {
        return DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(datetime)
    }
}