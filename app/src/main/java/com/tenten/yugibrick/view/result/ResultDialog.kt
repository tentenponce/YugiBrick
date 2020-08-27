package com.tenten.yugibrick.view.result

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDialog
import com.tenten.yugibrick.R
import com.tenten.yugibrick.view.base.BaseDialog
import com.tenten.yugibrick.view.common.util.NumberUtil
import com.tenten.yugibrick.view.result.Result.HIGH
import com.tenten.yugibrick.view.result.Result.LOW
import com.tenten.yugibrick.view.result.Result.MID
import kotlinx.android.synthetic.main.dialog_result.view.pb_result
import kotlinx.android.synthetic.main.dialog_result.view.ttext_probability
import kotlinx.android.synthetic.main.dialog_result.view.ttext_result
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 *
 * Created by Exequiel Egbert V. Ponce on 8/28/2020.
 */
class ResultDialog : BaseDialog() {

    companion object {
        private const val PROBABILITY = "PROBABILITY"

        fun newInstance(probability: Float): ResultDialog {
            val dialog = ResultDialog()

            val bundle = Bundle()
            bundle.putFloat(PROBABILITY, probability)
            dialog.arguments = bundle

            return dialog
        }
    }

    override val viewRes: Int = R.layout.dialog_result

    private val viewModel by viewModel<ResultViewModel>()

    lateinit var layoutView: View

    override fun initViews(view: View, savedInstanceState: Bundle?, dialog: AppCompatDialog) {
        layoutView = view

        val probability = arguments!!.getFloat(PROBABILITY)

        viewModel.setProbability(probability)
    }

    override fun initViewModels() {
        viewModel.statePercent.observe(this, { percent ->
            layoutView.pb_result.progress = percent.toInt()

            @SuppressLint("SetTextI18n")
            layoutView.ttext_probability.text =
                "${NumberUtil.formatDecimal(percent, 1, false)}%"
        })

        viewModel.stateResult.observe(this, { pair ->
            val percentage = pair.first
            val result = pair.second

            val formattedPercentage = NumberUtil.formatDecimal(percentage, 1, false)

            layoutView.ttext_result.text = when (result) {
                LOW -> String.format(getString(R.string.label_result_low), formattedPercentage)
                MID -> String.format(getString(R.string.label_result_mid), formattedPercentage)
                HIGH -> String.format(getString(R.string.label_result_high), formattedPercentage)
            }
        })
    }
}
