package com.example.getfriendlocation.view

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun getMilliFromDate(dateFormat: String?): Long {
    var date = Date()
    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
    try {
        date = formatter.parse(dateFormat)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return date.getTime()
}

fun getDate(input : String?) : String {
    if (input == null || input == "1")
        return ""
    val dateInMilliseconds = getMilliFromDate(input)
    val formatter = SimpleDateFormat("HH:mm dd-MM-yyyy")
    return formatter.format(Date(dateInMilliseconds))
}