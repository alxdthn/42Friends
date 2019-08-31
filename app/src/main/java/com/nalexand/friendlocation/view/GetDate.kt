package com.nalexand.friendlocation.view

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
    val time = (Calendar.getInstance().timeInMillis
            - getMilliFromDate(input)
            - TimeZone.getDefault().rawOffset) / 1000
    val seconds = time % 60
    val minutes = (time / 60) % 60
    val hours = (time / (60 * 60)) % 24
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}

class GetDate {
    fun endAt(input : String?) = getEndAt(input)
    fun beginAt(input: String?) = getBeginAt(input)
}
