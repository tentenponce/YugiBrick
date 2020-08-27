package com.tenten.yugibrick.view.calculator

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.tenten.ui.util.DialogFactory
import com.tenten.ui.util.afterTextChangeEvents
import com.tenten.yugibrick.R
import com.tenten.yugibrick.domain.model.Combo
import com.tenten.yugibrick.view.base.BaseFragment
import com.tenten.yugibrick.view.calculator.CalculatorViewModel.Companion.DEFAULT_DECK_SIZE
import com.tenten.yugibrick.view.calculator.CalculatorViewModel.Companion.DEFAULT_HAND_SIZE
import com.tenten.yugibrick.view.combo.ComboActivity
import com.tenten.yugibrick.view.common.view.ComboView
import com.tenten.yugibrick.view.result.ResultDialog
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_calculator.lin_combo_list
import kotlinx.android.synthetic.main.fragment_calculator.tbtn_add_combo
import kotlinx.android.synthetic.main.fragment_calculator.tbtn_calculate
import kotlinx.android.synthetic.main.fragment_calculator.tet_deck_size
import kotlinx.android.synthetic.main.fragment_calculator.tet_hand_size
import org.koin.androidx.viewmodel.ext.android.viewModel

class CalculatorFragment : BaseFragment() {

    companion object {
        private const val REQUEST_ADD_COMBO = 99
        private const val DIALOG_RESULT = "DIALOG_RESULT"

        fun newInstance(): CalculatorFragment {
            val fragment = CalculatorFragment()

            val bundle = Bundle()
            fragment.arguments = bundle

            return fragment
        }
    }

    override val viewRes: Int = R.layout.fragment_calculator

    private val viewModel by viewModel<CalculatorViewModel>()

    override fun initViews(savedInstanceState: Bundle?) {
        /* init view */
        tet_deck_size.getEditText().setText(DEFAULT_DECK_SIZE.toString())
        tet_hand_size.getEditText().setText(DEFAULT_HAND_SIZE.toString())

        /* init bindings */
        tbtn_add_combo.setOnClickListener {
            viewModel.addCombo()
        }

        tbtn_calculate.setOnClickListener {
            viewModel.calculate()
        }

        tet_deck_size.afterTextChangeEvents()
            .skipInitialValue()
            .subscribeBy(onNext = { editable ->
                viewModel.setDeckSize(editable.toString())
            })

        tet_hand_size.afterTextChangeEvents()
            .skipInitialValue()
            .subscribeBy(onNext = { editable ->
                viewModel.setHandSize(editable.toString())
            })
    }

    override fun initViewModels() {
        viewModel.stateAddCombo.observe(this, { pair ->
            val deckSize = pair.first
            val handSize = pair.second

            startActivityForResult(
                ComboActivity.getStartIntent(
                    context = context!!,
                    deckSize = deckSize,
                    handSize = handSize
                ),
                REQUEST_ADD_COMBO
            )
        })

        viewModel.stateCombos.observe(this, { combos ->
            lin_combo_list.removeAllViews()

            for (combo in combos) {
                val comboView = ComboView(context!!)

                comboView.set(combo) {
                    DialogFactory.createListDialog(
                        context!!,
                        context!!.getString(R.string.label_calc_action),
                        arrayOf("Delete")
                    ) { dialog, which ->
                        dialog.dismiss()

                        if (which == 0) {
                            viewModel.deleteCombo(combo.id)
                        }
                    }.show()
                }

                lin_combo_list.addView(comboView)
            }
        })

        viewModel.stateProbability.observe(this, { probability ->
            val dialog = ResultDialog.newInstance(probability)

            dialog.showNow(childFragmentManager, DIALOG_RESULT)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_ADD_COMBO &&
            resultCode == Activity.RESULT_OK
        ) {
            val combo = data!!.getParcelableExtra(ComboActivity.COMBO) as Combo

            viewModel.addCombo(combo)
        }
    }
}
