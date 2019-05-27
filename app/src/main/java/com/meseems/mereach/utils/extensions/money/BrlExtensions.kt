package com.meseems.mereach.utils.extensions.money

/**
 * Created by taynanbonaldo on 05/03/18
 */

fun String.toBrl(): String = this.toDouble().toBrl()

fun Double.toBrl(): String = String.format("R$ %.2f", this)

