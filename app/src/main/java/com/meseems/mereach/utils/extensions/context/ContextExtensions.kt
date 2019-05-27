package com.meseems.mereach.utils.extensions.context

import android.content.Context
import com.meseems.mereach.utils.extensions.string.convertToString

fun Context.readStringFromAssets(fileName: String): String {
    val inputStream = this.assets.open(fileName)
    return inputStream.convertToString()
}