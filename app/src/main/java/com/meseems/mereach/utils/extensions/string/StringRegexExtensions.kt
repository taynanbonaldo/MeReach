package com.meseems.mereach.utils.extensions.string

/**
 * Created by taynanbonaldo on 19/03/18
 */

infix fun String.removeAll(regex: Regex): String {
    return this.replace(regex, "")
}