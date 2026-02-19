package com.example.crudwithfirebase.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog

object DialogUtils {

    fun showConfirmation(
        context: Context,
        title: String,
        message: String,
        positiveButtonText: String = "OK",
        negativeButtonText: String = "Cancel",
        onPositiveClick: (() -> Unit)? = null,
        onNegativeClick: (() -> Unit)? = null,
        cancelable: Boolean = true
    ) {
        val dialog = AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveButtonText) { _, _ ->
                onPositiveClick?.invoke()
            }
            .setNegativeButton(negativeButtonText) { _, _ ->
                onNegativeClick?.invoke()
            }
            .setCancelable(cancelable)
            .create()

        dialog.show()
    }
}
