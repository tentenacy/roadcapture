package com.untilled.roadcapture.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

fun dateToString(year: Int, month: Int, dayOfMonth: Int): String =
    "${year}년 ${String.format("%02d", month)}월 ${String.format("%02d", dayOfMonth)}일"

fun dateToString(cal : Calendar): String {
    return dateToString(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH))
}

@SuppressLint("SimpleDateFormat")
fun dateToSnsFormat(str: String): String{
    val regString = str.substring(0, 19).replace('T', ' ')
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val regDate: Date = format.parse(regString)

    val curTime = System.currentTimeMillis()
    val regTime = regDate.time
    var diffTime = (curTime - regTime) / 1000

    var msg: String?
    if (diffTime < TIME_MAXIMUM.SEC) {
        msg = "방금 전"
    } else if (TIME_MAXIMUM.SEC.let { diffTime /= it; diffTime } < TIME_MAXIMUM.MIN) {
        msg = diffTime.toString() + "분 전"
    } else if (TIME_MAXIMUM.MIN.let { diffTime /= it; diffTime } < TIME_MAXIMUM.HOUR){
        msg = diffTime.toString() + "시간 전"
    } else if (TIME_MAXIMUM.HOUR.let { diffTime /= it; diffTime } < TIME_MAXIMUM.DAY) {
        msg = diffTime.toString() + "일 전"
    } else if (TIME_MAXIMUM.DAY.let { diffTime /= it; diffTime } < TIME_MAXIMUM.MONTH) {
        msg = diffTime.toString() + "달 전"
    } else {
        msg = diffTime.toString() + "년 전"
    }
    return msg
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

private object TIME_MAXIMUM {
    const val SEC = 60
    const val MIN = 60
    const val HOUR = 24
    const val DAY = 30
    const val MONTH = 12
}
