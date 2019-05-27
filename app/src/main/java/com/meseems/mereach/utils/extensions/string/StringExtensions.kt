package com.meseems.mereach.utils.extensions.string

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

/**
 * Created by taynanbonaldo on 06/02/18
 */

fun InputStream.convertToString(): String {
    val reader = BufferedReader(InputStreamReader(this))
    val sb = StringBuilder()
    try {
        var line = reader.readLine()
        while (line != null) {
            sb.append("$line\n")
            line = reader.readLine()
        }
        this.close()
    } catch (e: IOException) { throw e }

    return sb.toString()
}