package com.tenten.ui.util

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.tenten.ui.R

@Suppress("TooManyFunctions")
object DialogFactory {

    fun createProgressDialog(context: Context, message: String): Dialog {
        @SuppressLint("InflateParams")
        val layoutLoading = LayoutInflater.from(context).inflate(
            R.layout.layout_loading,
            null, false
        ) as ConstraintLayout

        val tvMessage = layoutLoading.findViewById(R.id.tv_message) as AppCompatTextView
        tvMessage.text = message

        val dialog = Dialog(context)
        dialog.setCancelable(false)
        dialog.setContentView(layoutLoading)
        return dialog
    }

    fun createProgressDialog(
        context: Context,
        @StringRes messageResource: Int
    ): Dialog {
        return createProgressDialog(context, context.getString(messageResource))
    }

    fun createErrorDialog(context: Context, title: String, message: String): Dialog {
        val alertDialog = AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(R.string.action_ok, null)
        return alertDialog.create()
    }

    fun createRetryDialog(
        context: Context,
        title: String,
        message: String,
        retryListener: DialogInterface.OnClickListener
    ): Dialog {
        return AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(R.string.action_ok, null)
            .setNeutralButton(R.string.action_retry, retryListener)
            .create()
    }

    fun createErrorDialog(
        context: Context,
        @StringRes titleResource: Int,
        @StringRes messageResource: Int
    ): Dialog {
        return createErrorDialog(
            context,
            context.getString(titleResource),
            context.getString(messageResource)
        )
    }

    fun createGenericErrorDialog(context: Context, message: String): Dialog {
        return createErrorDialog(
            context,
            context.getString(R.string.title_error_generic),
            message
        )
    }

    fun createGenericErrorDialog(context: Context, @StringRes messageResource: Int): Dialog {
        return createGenericErrorDialog(context, context.getString(messageResource))
    }

    fun createConfirmDialog(
        context: Context, titleResource: Int, msgResource: Int,
        yesListener: DialogInterface.OnClickListener
    ): AlertDialog {
        return createConfirmDialog(
            context, context.getString(titleResource),
            context.getString(msgResource), yesListener
        )
    }

    fun createConfirmDialog(
        context: Context, title: String, msg: String,
        yesListener: DialogInterface.OnClickListener
    ): AlertDialog {
        return AlertDialog.Builder(context).setTitle(title)
            .setMessage(msg)
            .setPositiveButton("Yes", yesListener)
            .setNegativeButton("No") { dialog, _ -> dialog.cancel() }.create()
    }

    fun createMessageDialog(
        context: Context,
        @StringRes titleResource: Int,
        @StringRes messageResource: Int
    ): Dialog {

        return createMessageDialog(
            context,
            context.getString(titleResource),
            context.getString(messageResource)
        )
    }

    fun createConfirmOkDialog(
        context: Context, title: String, msg: String,
        yesListener: DialogInterface.OnClickListener
    ): AlertDialog {
        return AlertDialog.Builder(context).setTitle(title)
            .setMessage(msg)
            .setPositiveButton("Ok", yesListener).create()
    }

    fun createMessageDialog(context: Context, title: String, msg: String): AlertDialog {
        return AlertDialog.Builder(context).setTitle(title)
            .setMessage(msg)
            .setPositiveButton("Ok") { dialogInterface, _ -> dialogInterface.dismiss() }.create()
    }

    fun createListDialog(
        context: Context,
        title: String,
        choices: Array<String>,
        listener: DialogInterface.OnClickListener
    ): AlertDialog {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)

        builder.setItems(choices, listener)
        return builder.create()
    }

    interface OnDateSetListener {
        fun onSet(year: Int, month: Int, day: Int)
    }
}
