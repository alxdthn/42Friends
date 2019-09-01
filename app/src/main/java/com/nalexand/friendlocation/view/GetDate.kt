package com.nalexand.friendlocation.view

import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun getMilliFromDate(input: String?): Long {
    var date = Date()
    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    try {
        date = formatter.parse(input)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return date.getTime()
}

fun getEndAt(input : String?) : String {
    if (input == null || input == "1")
        return ""
    val dateInMilliseconds = getMilliFromDate(input)
    val formatter = SimpleDateFormat("HH:mm dd-MM-yyyy", Locale.getDefault())
    return formatter.format(Date(dateInMilliseconds + TimeZone.getDefault().rawOffset))
}

fun getBeginAt(input: String?) : String {
    val time = Calendar.getInstance().timeInMillis - getMilliFromDate(input) - TimeZone.getDefault().rawOffset
    val h = time / 3600000
    val m = time / 60000 % 60
    val s = time / 1000 % 60
    return String.format("%02d:%02d:%02d", h, m, s)
}

class GetDate {
    fun endAt(input : String?) = getEndAt(input)
    fun beginAt(input: String?) = getBeginAt(input)
}
