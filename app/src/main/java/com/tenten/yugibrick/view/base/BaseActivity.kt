package com.tenten.yugibrick.view.base

import android.app.Dialog
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.tenten.ui.TToolbar
import com.tenten.ui.util.DialogFactory
import com.tenten.yugibrick.App
import com.tenten.yugibrick.R
import java.util.Timer
import java.util.TimerTask
import kotlin.random.Random

/**
 *
 * Created by Exequiel Egbert V. Ponce on 4/1/2020.
 */

@Suppress("TooManyFunctions")
abstract class BaseActivity : AppCompatActivity() {

    companion object {
        private const val LOADING_MESSAGE_PERIOD: Long = 3.5.toLong() * 1000
    }

    abstract val viewRes: Int

    abstract fun getToolbar(): TToolbar?

    abstract fun baseViewModel(): BaseViewModel?

    abstract fun initViews(savedInstanceState: Bundle?)

    abstract fun initViewModels()

    private var loadingDialog: Dialog? = null
    private val dialogs = arrayListOf<Dialog>()

    private var timer: Timer? = null
    private var timerTask: TimerTask? = null

    private var loadingObserver: Observer<String>? = null
    private var loadingMessage = MutableLiveData<String>()
    private lateinit var loadingMessages: Array<String>

    val genericLoadingObserver = Observer<Boolean> {
        if (it) showLoading() else hideLoading()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewRes)

        loadingMessage.value = getString(R.string.loading)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        /* init string resources */
        loadingMessages = arrayOf(
            getString(R.string.loading_2),
            getString(R.string.loading_3),
            getString(R.string.loading_4),
            getString(R.string.loading_5),
        )

        /* init abstract methods */
        initViews(savedInstanceState)
        initViewModels()
    }

    override fun onPause() {
        cleanUpDialogs()

        super.onPause()
    }

    override fun onDestroy() {
        cleanUpDialogs()
        super.onDestroy()
    }

    fun showLoading(message: String) {
        if (!isFinishing) {
            if (loadingDialog == null) {
                loadingDialog = DialogFactory.createProgressDialog(this, message)

                loadingDialog!!.show()
            } else {
                if (!loadingDialog!!.isShowing) {
                    loadingDialog!!.show()
                } else { //if loading is showing, update the text
                    (loadingDialog!!.findViewById(R.id.tv_message) as AppCompatTextView).text =
                        message
                }
            }
        }
    }

    fun showLoading() {
        showLoading(getString(R.string.loading))

        loadingMessage.value = getString(R.string.loading)
        loadingObserver?.let { loadingMessage.removeObserver(it) }
        loadingObserver = Observer {
            (loadingDialog!!.findViewById(R.id.tv_message) as AppCompatTextView).text =
                it
        }
        loadingMessage.observe(this, loadingObserver!!)

        resetLoadingMessageTimer()
    }

    fun hideLoading() {
        if (loadingDialog != null && loadingDialog!!.isShowing) {
            loadingDialog!!.dismiss()
            stopLoadingMessageTimer()
        }

        loadingObserver?.let { loadingMessage.removeObserver(it) }
    }

    private fun resetLoadingMessageTimer() {
        stopLoadingMessageTimer()
        startLoadingMessageTimer()
    }

    private fun stopLoadingMessageTimer() {
        if (timer != null) {
            timer!!.cancel()
            timerTask!!.cancel()
            timer = null
            timerTask = null
        }
    }

    private fun startLoadingMessageTimer() {
        if (timer == null) {
            timer = Timer(false)
            timerTask = object : TimerTask() {
                override fun run() {
                    loadingMessage.postValue(
                        loadingMessages[Random.nextInt(loadingMessages.size)]
                    )
                }
            }

            timer!!.schedule(timerTask, LOADING_MESSAGE_PERIOD, LOADING_MESSAGE_PERIOD)
        }
    }

    fun showGenericErrorDialog(stringRes: Int) {
        if (!isFinishing) {
            val dialog = DialogFactory.createGenericErrorDialog(this, stringRes)
            dialogs.add(dialog)
            dialog.show()
        }
    }

    fun showErrorDialog(title: String, message: String) {
        if (!isFinishing) {
            val dialog = DialogFactory.createErrorDialog(this, title, message)
            dialogs.add(dialog)
            dialog.show()
        }
    }

    fun showErrorDialog(titleRes: Int, stringRes: Int) {
        if (!isFinishing) {
            val dialog = DialogFactory.createErrorDialog(this, titleRes, stringRes)
            dialogs.add(dialog)
            dialog.show()
        }
    }

    fun showShortToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun showLongToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun showShortToast(@StringRes messageRes: Int) {
        Toast.makeText(this, messageRes, Toast.LENGTH_SHORT).show()
    }

    fun showLongToast(@StringRes messageRes: Int) {
        Toast.makeText(this, messageRes, Toast.LENGTH_LONG).show()
    }

    fun getApp(): App {
        return application as App
    }

    private fun cleanUpDialogs() {
        for (dialog in dialogs) {
            if (dialog.isShowing) {
                dialog.cancel()
            }
        }

        if (loadingDialog != null && loadingDialog!!.isShowing) {
            loadingDialog!!.cancel()
        }
    }
}
