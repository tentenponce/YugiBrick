package com.tenten.yugibrick.view.calculator

import android.os.Bundle
import com.tenten.yugibrick.R
import com.tenten.yugibrick.view.base.BaseFragment
import com.tenten.yugibrick.view.combo.ComboActivity
import kotlinx.android.synthetic.main.fragment_calculator.tbtn_add_combo

class CalculatorFragment : BaseFragment() {

    companion object {
        fun newInstance(): CalculatorFragment {
            val fragment = CalculatorFragment()

            val bundle = Bundle()
            fragment.arguments = bundle

            return fragment
        }
    }

    override val viewRes: Int = R.layout.fragment_calculator

    override fun initViews(savedInstanceState: Bundle?) {
        /* init bindings */
        tbtn_add_combo.setOnClickListener {
            startActivity(ComboActivity.getStartIntent(context!!))
        }
    }

    override fun initViewModels() {
    }
}
