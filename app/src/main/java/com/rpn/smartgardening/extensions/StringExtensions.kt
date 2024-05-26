package com.rpn.exchangebook.extensions

import android.widget.EditText

fun EditText.getHour(): Int? {
    return if (this.text.toString().isEmpty()) null
    else this.text.toString().trim().split(":").firstOrNull()?.toInt()
}

fun EditText.getMinute(): Int? {
    return if (this.text.toString().isEmpty()) null
    else this.text.toString().trim().split(":").lastOrNull()?.toInt()
}

