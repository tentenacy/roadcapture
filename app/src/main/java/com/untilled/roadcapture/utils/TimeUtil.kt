package com.untilled.roadcapture.utils

import java.util.*

fun dateToString(year: Int, month: Int, dayOfMonth: Int): String =
    "${year}년 ${String.format("%02d", month)}월 ${String.format("%02d", dayOfMonth)}일"

fun dateToString(cal : Calendar): String {
    return dateToString(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH))
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
