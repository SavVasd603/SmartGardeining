package com.rpn.smartgardening.utils

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.O
import android.util.Log
import android.view.LayoutInflater
import com.rpn.smartgardening.databinding.DialogLoadingBinding
import java.text.SimpleDateFormat
import java.util.*

object GeneralUtils {
    fun Context.loadingDialog(onShow: (dialog: Dialog) -> Unit) {
        var bindingView = DialogLoadingBinding.inflate(LayoutInflater.from(this))
        val dialog = Dialog(this)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(bindingView.root)
        dialog.show()
        onShow(dialog)
    }
}