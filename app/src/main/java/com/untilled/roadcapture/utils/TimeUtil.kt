package com.untilled.roadcapture.utils

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*

fun dateToString(year: Int, month: Int, dayOfMonth: Int): String =
    "${year}년 ${String.format("%02d", month)}월 ${String.format("%02d", dayOfMonth)}일"

fun dateToString(cal : Calendar): String {
    return dateToString(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH))
}

@SuppressLint("SimpleDateFormat")
fun dateToSnsFormat(date: LocalDateTime): String{

    val curDate = LocalDateTime.now()
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

fun getFilterDate(filter: Int): String{
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS")
    val calendar = Calendar.getInstance()
    when(filter){
        TimeUtil.TODAY ->{
        }

        TimeUtil.WEEK -> {
            calendar.add(Calendar.DATE, -7)
        }

        TimeUtil.MONTH -> {
            calendar.add(Calendar.MONTH, -1)
        }

        TimeUtil.YEAR -> {
            calendar.add(Calendar.YEAR, -1)
        }
    }
    val time = calendar.time
    val date = format.format(time)
    return date.toString()
}

fun getFilterDate(str: String): String{
    val year = str.substring(0,4).toInt()
    val month = str.substring(6,8).toInt() - 1
    val day = str.substring(10,12).toInt()
    val calendar = getCalendar(year,month,day)
    val format = SimpleDateFormat("yyyy-MM-dd'T'00:00:00.000000000")
    val time = calendar.time
    val date = format.format(time)
    return date.toString()
}

fun getCalendar(year: Int, month: Int, dayOfMonth: Int): Calendar {
    val cal = Calendar.getInstance()
    cal.set(year, month, dayOfMonth)
    return cal
}

fun getCalendar(date : String): Calendar {
    val token = date.split(" ")
    val cal = Calendar.getInstance()
    cal.set(token[0].substring(0,4).toInt(), token[1].substring(0, 2).toInt() - 1, token[2].substring(0, 2).toInt())
    return cal
}

fun compareDate(startDate: Calendar, endDate: Calendar): Boolean =
    !startDate.before(endDate)

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

