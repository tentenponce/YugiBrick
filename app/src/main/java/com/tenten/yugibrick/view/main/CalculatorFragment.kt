package com.tenten.yugibrick.view.main

import android.os.Bundle
import com.tenten.yugibrick.R
import com.tenten.yugibrick.view.base.BaseFragment

class CalculatorFragment : BaseFragment() {

    companion object {
        fun newInstance(): CalculatorFragment {
            val fragment = CalculatorFragment()

            val bundle = Bundle()
            fragment.arguments = bundle

            return fragment
        }
    }

    override val viewRes: Int = R.layout.fragment_main

    override fun initViews(savedInstanceState: Bundle?) {
    }

    override fun initViewModels() {
    }
}
