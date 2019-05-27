package com.meseems.mereach.utils.extensions.date

import java.util.*

/**
 * Created by taynanbonaldo on 28/03/18
 */
fun Calendar.yearsFromNow(): Int {
    val now = Calendar.getInstance()
    return Math.floor((now.timeInMillis - this.timeInMillis) / 3.156e+10).toInt()
}