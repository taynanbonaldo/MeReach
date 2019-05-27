package com.meseems.mereach.utils.extensions.string

import com.meseems.mereach.utils.extensions.date.toCalendar
import com.meseems.mereach.utils.extensions.date.yearsFromNow
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by taynanbonaldo on 20/02/18
 */
fun String.isValidDate(pattern: String): Boolean {
    val sdf = SimpleDateFormat(pattern)
    sdf.isLenient = false
    return try {
        sdf.parse(this)
        true
    } catch (e: ParseException) {
        false
    }
}

fun String.isValidBirthdate(): Boolean {
    try {
        val date = this.parseDate().toCalendar()
        //Minimum age is 16
        if (date.yearsFromNow() < 16) return false
        //People from 18th century not allowed
        if (date[Calendar.YEAR] <= 1900) return false
        return true
    } catch (e: ParseException) {
        return false
    }
}

fun String.parseDate(): Date {
    var dfOriginal: SimpleDateFormat = when {
        this matches "[0-9]{4}-[0-9]{2}-[0-9]{2}".toRegex() -> SimpleDateFormat("yyyy-MM-dd")
        this matches "[0-9]{2}/[0-9]{2}/[0-9]{4}".toRegex() -> SimpleDateFormat("dd/MM/yyyy")
        this matches "[0-9]{8}".toRegex() -> SimpleDateFormat("ddMMyyyy")
        this matches "[0-9]{2}/[0-9]{2}/[0-9]{4} [0-9]{2}:[0-9]{2}:[0-9]{2}".toRegex() -> SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        else -> SimpleDateFormat("dd.MM.yyyy")
    }
    return dfOriginal.parse(this)
}

fun String.formatDate(pattern: String): String {
    val format = SimpleDateFormat(pattern)
    return format.format(this.parseDate())
}