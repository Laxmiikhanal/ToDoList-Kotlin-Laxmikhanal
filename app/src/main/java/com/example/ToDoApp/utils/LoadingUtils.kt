package com.example.ToDoApp.utils

import android.app.Activity
import android.app.AlertDialog
import com.example.ToDoApp.R

class LoadingUtils(private val activity: Activity) {
    private var alertDialog: AlertDialog? = null

    fun show() {
        // Check if the dialog is already showing to avoid multiple instances
        if (alertDialog == null) {
            val builder = AlertDialog.Builder(activity)

            val dialogView = activity.layoutInflater.inflate(R.layout.loading, null)

            builder.setView(dialogView)
            builder.setCancelable(false)

            alertDialog = builder.create()
            alertDialog?.show()
        }
    }

    fun dismiss() {
        // Check if the dialog is not null and is showing before dismissing
        alertDialog?.let {
            if (it.isShowing) {
                it.dismiss()
                alertDialog = null // Reset the dialog reference
            }
        }
    }
}