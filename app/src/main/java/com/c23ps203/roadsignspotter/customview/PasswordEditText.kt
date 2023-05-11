package com.c23ps203.roadsignspotter.customview

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.c23ps203.roadsignspotter.R

class PasswordEditText(context: Context, attrs: AttributeSet?) : AppCompatEditText(context, attrs) {

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        error = if ((text?.length ?: 0) < 8 && !text.isNullOrEmpty()) {
            resources.getString(R.string.password_error)
        } else {
            null
        }
    }
}
