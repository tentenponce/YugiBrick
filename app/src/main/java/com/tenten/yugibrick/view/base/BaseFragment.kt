package com.tenten.yugibrick.view.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.tenten.yugibrick.App

@Suppress("TooManyFunctions")
abstract class BaseFragment : Fragment() {

    abstract val viewRes: Int

    abstract fun initViews(savedInstanceState: Bundle?)

    abstract fun initViewModels()

    lateinit var genericLoadingObserver: Observer<Boolean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        genericLoadingObserver = getBaseActivity().genericLoadingObserver
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(viewRes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initViewModels()
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

    fun showShortToast(message: String?) {
        getBaseActivity().showShortToast(message)
    }

    fun showLongToast(message: String?) {
        getBaseActivity().showLongToast(message)
    }

    fun showShortToast(@StringRes messageRes: Int) {
        getBaseActivity().showShortToast(messageRes)
    }

    fun showLongToast(@StringRes messageRes: Int) {
        getBaseActivity().showLongToast(messageRes)
    }

    fun getBaseActivity(): BaseActivity {
        return activity as BaseActivity
    }

    fun getApp(): App {
        return activity?.application as App
    }
}
