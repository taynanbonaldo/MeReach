package com.meseems.mereach.utils.extensions.date

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by taynanbonaldo on 28/03/18.
 */

val DEFAULT_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.sss"

fun Date.toCalendar(): Calendar {
    return Calendar.getInstance().apply { time = this@toCalendar }
}

fun Calendar.getTimestampFormatted(datePattern: String? = DEFAULT_DATE_PATTERN): String {
    return SimpleDateFormat(datePattern).format(time)
}