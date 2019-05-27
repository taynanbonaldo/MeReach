package com.meseems.mereach.utils.extensions.view

import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.StringRes

/**
 * Created by taynanbonaldo on 3/16/18
 */

fun EditText.isBlank(): Boolean = this.text.toString().isBlank()

fun TextView.setError(@StringRes error: Int) {
    this.error = this.context.getString(error)
}

fun TextView.isBlank(): Boolean = this.text.isBlank()

fun TextView.getString(): String = this.text.toString().trim()

fun View.setMargin(leftMargin: Int? = null, topMargin: Int? = null,
                   rightMargin: Int? = null, bottomMargin: Int? = null) {
    val params = layoutParams as ViewGroup.MarginLayoutParams
    params.setMargins(
            leftMargin ?: params.leftMargin,
            topMargin ?: params.topMargin,
            rightMargin ?: params.rightMargin,
            bottomMargin ?: params.bottomMargin)
    layoutParams = params
}