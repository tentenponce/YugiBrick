package com.tenten.yugibrick.view.combo

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.tenten.ui.TToolbar
import com.tenten.ui.util.DialogFactory
import com.tenten.yugibrick.R
import com.tenten.yugibrick.domain.model.Card
import com.tenten.yugibrick.view.base.BaseActivity
import com.tenten.yugibrick.view.base.BaseViewModel
import com.tenten.yugibrick.view.card.CardDialog
import kotlinx.android.synthetic.main.activity_combo.lin_card_list
import kotlinx.android.synthetic.main.activity_combo.tbtn_add_card
import kotlinx.android.synthetic.main.activity_combo.ttoolbar
import kotlinx.android.synthetic.main.item_card.view.ttext_copies
import kotlinx.android.synthetic.main.item_card.view.ttext_name
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 *
 * Created by Exequiel Egbert V. Ponce on 8/27/2020.
 */
class ComboActivity(override val viewRes: Int = R.layout.activity_combo) : BaseActivity() {

    companion object {
        private const val DIALOG_CARD = "DIALOG_CARD"

        fun getStartIntent(context: Context) =
            Intent(context, ComboActivity::class.java)
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
    }

    override fun initViewModels() {
        viewModel.stateCards.observe(this, Observer(::renderCards))
        viewModel.stateShowCardDialog.observe(this, {
            cardDialog = CardDialog()

            cardDialog!!.showNow(supportFragmentManager, DIALOG_CARD)
        })
    }

    private fun renderCards(cards: List<Card>) {
        cardDialog?.dismiss()
        
        lin_card_list.removeAllViews()
        for (card in cards) {
            val itemCard = layoutInflater.inflate(R.layout.item_card, lin_card_list, false)

            itemCard.setOnClickListener {
                DialogFactory.createListDialog(
                    this,
                    getString(R.string.label_combo_action),
                    arrayOf("Delete")
                ) { dialog, which ->
                    dialog.dismiss()
                    when (which) {
                        0 -> viewModel.delete(card)
                    }
                }.show()
            }

            itemCard.ttext_name.text = card.name

            @SuppressLint("SetTextI18n")
            itemCard.ttext_copies.text =
                "${card.copies} ${if (card.copies > 1) "Copies" else "Copy"}"

            lin_card_list.addView(itemCard)
        }
    }
}
