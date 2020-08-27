package com.tenten.yugibrick.view.combo

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.lifecycle.Observer
import com.tenten.ui.TToolbar
import com.tenten.ui.util.DialogFactory
import com.tenten.yugibrick.R
import com.tenten.yugibrick.domain.model.Card
import com.tenten.yugibrick.view.base.BaseActivity
import com.tenten.yugibrick.view.base.BaseViewModel
import com.tenten.yugibrick.view.card.CardDialog
import com.tenten.yugibrick.view.common.view.CardView
import kotlinx.android.synthetic.main.activity_combo.lin_card_list
import kotlinx.android.synthetic.main.activity_combo.tbtn_add_card
import kotlinx.android.synthetic.main.activity_combo.tbtn_add_combo
import kotlinx.android.synthetic.main.activity_combo.ttoolbar
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 *
 * Created by Exequiel Egbert V. Ponce on 8/27/2020.
 */
class ComboActivity(override val viewRes: Int = R.layout.activity_combo) : BaseActivity() {

    companion object {
        const val COMBO = "COMBO"
        const val DECK_SIZE = "DECK_SIZE"
        const val HAND_SIZE = "HAND_SIZE"

        private const val DIALOG_CARD = "DIALOG_CARD"

        fun getStartIntent(context: Context, deckSize: Int, handSize: Int) =
            Intent(context, ComboActivity::class.java)
                .putExtra(DECK_SIZE, deckSize)
                .putExtra(HAND_SIZE, handSize)
    }

    private val viewModel by viewModel<ComboViewModel>()

    override fun getToolbar(): TToolbar? = ttoolbar
    override fun baseViewModel(): BaseViewModel? = viewModel

    private var cardDialog: CardDialog? = null

    override fun initViews(savedInstanceState: Bundle?) {
        /* init bindings */
        tbtn_add_card.setOnClickListener {
            viewModel.addCard()
        }

        tbtn_add_combo.setOnClickListener {
            viewModel.submitCombo()
        }

        /* init arguments */
        viewModel.init(
            deckSize = intent.getIntExtra(DECK_SIZE, 0),
            handSize = intent.getIntExtra(HAND_SIZE, 0)
        )
    }

    override fun initViewModels() {
        viewModel.stateCards.observe(this, Observer(::renderCards))
        viewModel.stateShowCardDialog.observe(this, {
            cardDialog = CardDialog()

            cardDialog!!.showNow(supportFragmentManager, DIALOG_CARD)
        })

        viewModel.stateShowDone.observe(this, { show ->
            tbtn_add_combo.visibility = if (show) View.VISIBLE else View.GONE
        })

        viewModel.stateSubmitCombo.observe(this, { combo ->
            val intent = Intent()
                .putExtra(COMBO, combo)

            setResult(Activity.RESULT_OK, intent)
            finish()
        })
    }

    private fun renderCards(cards: List<Card>) {
        cardDialog?.dismiss()

        lin_card_list.removeAllViews()
        for (card in cards) {
            val cardView = CardView(this)

            cardView.set(card) {
                DialogFactory.createListDialog(
                    this,
                    getString(R.string.label_combo_action),
                    arrayOf("Delete"),
                ) { dialog, which ->
                    dialog.dismiss()
                    when (which) {
                        0 -> viewModel.delete(card)
                    }
                }.show()
            }

            val cardParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT
            )

            cardParams.setMargins(0, 0, 0, resources.getDimension(R.dimen.xs_space).toInt())

            cardView.layoutParams = cardParams

            lin_card_list.addView(cardView)
        }
    }
}
