package com.alamin.attendanceassistant.utils

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class CustomAlertDialog @Inject constructor(@ActivityContext val context: Context) {

    fun createDialog(title:String, message:String,buttonColor: Int, dialogCallBack: ApplicationsCallBack.SetOnAlertDialogClickListener){
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("Yes", object : DialogInterface.OnClickListener{
            override fun onClick(dialogInterface: DialogInterface?, p1: Int) {
                dialogCallBack.onPositive()
                dialogInterface?.dismiss()

            }

        })
        builder.setNegativeButton("No", object : DialogInterface.OnClickListener{
            override fun onClick(dialogInterface: DialogInterface?, p1: Int) {
                dialogCallBack.onNegative()
                dialogInterface?.dismiss()
            }

        })
        val dialog = builder.create()
        dialog.show()
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(context,
            buttonColor))
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(context,
            buttonColor))
    }
}