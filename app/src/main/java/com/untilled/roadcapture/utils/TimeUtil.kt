package com.untilled.roadcapture.utils

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import java.util.*

@SuppressLint("SimpleDateFormat")
fun dateToSnsFormat(date: LocalDateTime): String{

    val curDate = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime()
    var diffDate = date.until(curDate, ChronoUnit.SECONDS)

    var msg: String?
    if (diffDate < TimeUtil.SEC) {
        msg = diffDate.toString() + "초 전"
    } else if (TimeUtil.SEC.let { diffDate /= it; diffDate } < TimeUtil.MIN) {
        msg = diffDate.toString() + "분 전"
    } else if (TimeUtil.MIN.let { diffDate /= it; diffDate } < TimeUtil.HOUR){
        msg = diffDate.toString() + "시간 전"
    } else if (TimeUtil.HOUR.let { diffDate /= it; diffDate } < TimeUtil.DAY) {
        msg = diffDate.toString() + "일 전"
    } else if (TimeUtil.DAY.let { diffDate /= it; diffDate } < TimeUtil.A_MONTH) {
        msg = diffDate.toString() + "달 전"
    } else {
        TimeUtil.A_MONTH.let { diffDate /= it; diffDate }
        msg = diffDate.toString() + "년 전"
    }
    return msg
}

fun getTodayCalendar(): Calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"),Locale.KOREA)

class TimeUtil{
    companion object {
        const val SEC = 60
        const val MIN = 60
        const val HOUR = 24
        const val DAY = 30
        const val A_MONTH = 12
        const val WHOLE = 1
        const val TODAY = 2
        const val WEEK = 3
        const val MONTH = 4
        const val YEAR = 5
    }
}

////////////////////////////////////////////////////////////////////////////////////////////////////

fun yesterday(): Date {
    val cal = Calendar.getInstance()
    cal.time = today()
    cal.add(Calendar.DAY_OF_MONTH, -1)
    return cal.time
}

fun today(): Date {
    val cal = Calendar.getInstance()
    cal.time = Date()
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    return cal.time
}

fun tomorrow(): Date {
    val cal = Calendar.getInstance()
    cal.time = today()
    cal.add(Calendar.DAY_OF_MONTH, 1)
    return cal.time
}

fun sevenDaysAgo(): Date {
    val cal = Calendar.getInstance()
    cal.time = today()
    cal.add(Calendar.DAY_OF_MONTH, -7)
    return cal.time
}

fun Date?.toSimpleDateFormat(): String {
    return this?.run { SimpleDateFormat("yy.MM.dd").format(this) } ?: ""
}

fun Date?.toDateFormat(): String {
    return this?.run { SimpleDateFormat("yyyy-MM-dd").format(this) } ?: ""
}

fun Date?.toDateTimeFormat(): String {
    return this?.run { SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS").format(this) } ?: ""
}

fun dateFromDateTime(dateText: String): Date {
    return SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText)
}

fun dateFrom(dateText: String): Date {
    return SimpleDateFormat("yyyy-MM-dd").parse(dateText)
}

fun Date.start(): Date {
    val cal = Calendar.getInstance()
    cal.time = this
    cal.set(Calendar.HOUR_OF_DAY, 0)
    cal.set(Calendar.MINUTE, 0)
    cal.set(Calendar.SECOND, 0)
    cal.set(Calendar.MILLISECOND, 0)
    return cal.time
}

fun Date.tomorrow(): Date {
    val cal = Calendar.getInstance()
    cal.time = this
    cal.add(Calendar.DAY_OF_MONTH, 1)
    return cal.time
}

fun Date.year(): Int {
    val cal = Calendar.getInstance()
    cal.time = this
    return cal.get(Calendar.YEAR)
}

fun Date.month(): Int {
    val cal = Calendar.getInstance()
    cal.time = this
    return cal.get(Calendar.MONTH) + 1
}

fun Date.day(): Int {
    val cal = Calendar.getInstance()
    cal.time = this
    return cal.get(Calendar.DAY_OF_MONTH)
}

fun Date.minusDay(day: Int): Date {
    val cal = Calendar.getInstance()
    cal.time = this
    cal.add(Calendar.DAY_OF_MONTH, -day)
    return cal.time
}

fun Date.minusMonth(month: Int): Date {
    val cal = Calendar.getInstance()
    cal.time = this
    cal.add(Calendar.MONTH, -month)
    return cal.time
}

fun Date.minusYear(year: Int): Date {
    val cal = Calendar.getInstance()
    cal.time = this
    cal.add(Calendar.YEAR, -year)
    return cal.time
}

fun calendarFrom(dateText: String): Calendar {
    return dateText.takeIf { it != "-" }?.let {
        val token = dateText.split("-")
        Calendar.getInstance().apply {
            set(token[0].substring(0,4).toInt(), token[1].substring(0, 2).toInt() - 1, token[2].substring(0, 2).toInt())
        }
    } ?: run {
        Calendar.getInstance().apply {
            time = Date()
        }
    }
}