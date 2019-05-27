package com.meseems.mereach.utils.extensions.string

import android.graphics.Typeface
import android.text.Html
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.StyleSpan
import com.meseems.mereach.utils.extensions.money.toBrl

/**
 * Created by taynanbonaldo on 05/03/18
 */
fun SpannableStringBuilder.addBoldLabel(label: String, desc: String): SpannableStringBuilder {
    addEnter()
    this.append(label.toBoldText())
            .append(" ")
            .append(desc)
    return this
}


fun SpannableStringBuilder.addBoldText(label: String, desc: String): SpannableStringBuilder {
    addEnter()
    this.append(label)
            .append(" ")
            .append(desc.toBoldText())
    return this
}


fun SpannableStringBuilder.addBrlValue(label: String, desc: Double): SpannableStringBuilder {
    addEnter()
    this.append(label.toBoldText())
            .append(" ")
            .append(desc.toBrl())
    return this
}


private fun SpannableStringBuilder.addEnter() {
    if (this.isNotEmpty()) {
        this.append("\n")
    }
}

private fun String.toBoldText(): SpannableString {
    val spannable = SpannableString(this)
    spannable.setSpan(StyleSpan(Typeface.BOLD), 0, spannable.length, 0)
    return spannable
}

fun String.fromHtml(): Spanned {
    return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(this)
    }
}

