package com.meseems.mereach.utils.extensions.view

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.meseems.mereach.utils.extensions.string.removeAll
import io.reactivex.subjects.PublishSubject

/**
 * Created by taynanbonaldo on 19/03/18
 */

fun String.unmask(): String = this removeAll "\\D".toRegex()

fun EditText.mask(mask: String, subject: PublishSubject<String>? = null) {
    this.addTextChangedListener(create(this, mask, subject))
}

fun EditText.watch(subject: PublishSubject<String>? = null) {
    this.addTextChangedListener(create(this, null, subject))
}

fun create(ediTxt: EditText, mask: String? = null,
           subject: PublishSubject<String>? = null): TextWatcher {

    return object : TextWatcher {

        internal var isUpdating: Boolean = false
        internal var old = ""

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            try {
                var textMasked = updateMask(s)
                subject?.let { it.onNext(textMasked) }

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        override fun afterTextChanged(editable: Editable) {}

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        private fun updateMask(s: CharSequence): String {

            if(mask == null)
                return s.toString()

            val str = s.toString().unmask()
            var masked = ""
            if (isUpdating) {
                old = str
                isUpdating = false
                return str
            }

            var i = 0
            for (m in mask.toCharArray()) {
                if (m != '#' && str.length > old.length) {
                    masked += m
                    continue
                }
                try {
                    masked += str.get(i)
                } catch (e: Exception) {
                    break
                }
                i++
            }
            isUpdating = true
            ediTxt.setText(masked)
            ediTxt.setSelection(masked.length)

            return masked
        }
    }
}