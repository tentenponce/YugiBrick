package com.tenten.yugibrick.view.base

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDialog
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.FragmentManager
import com.tenten.yugibrick.App

/**
 *
 * Created by Exequiel Egbert V. Ponce on 4/21/2020.
 */
@Suppress("TooManyFunctions")
abstract class BaseDialog : AppCompatDialogFragment() {

    abstract val viewRes: Int

    abstract fun initViews(view: View, savedInstanceState: Bundle?, dialog: AppCompatDialog)

    abstract fun initViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as AppCompatDialog
        val contentView = View.inflate(context, viewRes, null)
        dialog.setContentView(contentView)

        initViews(contentView, savedInstanceState, dialog)

        return dialog
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initViewModels()
    }

    override fun showNow(manager: FragmentManager, tag: String?) {
        if (manager.findFragmentByTag(tag) == null) {
            if (dialog == null) {
                super.showNow(manager, tag)
            } else if (dialog?.isShowing == false) {
                super.showNow(manager, tag)
            }
        }
    }

    fun showLoading() {
        if (activity != null) {
            (activity as BaseActivity).showLoading()
        }
    }

    fun showLoading(message: String) {
        if (activity != null) {
            (activity as BaseActivity).showLoading(message)
        }
    }

    fun hideLoading() {
        if (activity != null) {
            (activity as BaseActivity).hideLoading()
        }
    }

    fun showGenericErrorDialog(stringRes: Int) {
        if (activity != null) {
            (activity as BaseActivity).showGenericErrorDialog(stringRes)
        }
    }

    fun showErrorDialog(title: String, message: String) {
        if (activity != null) {
            (activity as BaseActivity).showErrorDialog(title, message)
        }
    }

    fun showErrorDialog(titleRes: Int, stringRes: Int) {
        if (activity != null) {
            (activity as BaseActivity).showErrorDialog(titleRes, stringRes)
        }
    }

    fun showShortToast(message: String) {
        getBaseActivity().showShortToast(message)
    }

    fun showLongToast(message: String) {
        getBaseActivity().showLongToast(message)
    }

    fun getApp(): App {
        return activity?.application as App
    }

    fun getBaseActivity(): BaseActivity {
        return activity as BaseActivity
    }
}
