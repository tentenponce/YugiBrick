package com.tenten.yugibrick.view.common.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.LinearLayoutCompat
import com.tenten.ui.TText
import com.tenten.yugibrick.R
import com.tenten.yugibrick.domain.model.Combo
import com.tenten.yugibrick.view.common.util.NumberUtil

/**
 *
 * Created by Exequiel Egbert V. Ponce on 8/27/2020.
 */
class ComboView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayoutCompat(context, attrs, defStyleAttr) {

    private var ttextComboName: TText
    private var linCardList: LinearLayoutCompat

    init {
        inflate(getContext(), R.layout.view_combo, this)

        ttextComboName = findViewById(R.id.ttext_combo_name)
        linCardList = findViewById(R.id.lin_card_list)
    }

    fun set(combo: Combo, listener: OnClickListener?) {
        setOnClickListener(listener)

        if (listener == null) {
            isClickable = false
            isFocusable = false
        }

        ttextComboName.text = String.format(
            context.getString(R.string.title_item_combo),
            combo.cards.size.toString(),
            NumberUtil.formatDecimal((combo.probability * 100), 1, false)
        )

        for (card in combo.cards) {
            val cardView = CardView(context)

            cardView.set(card, null)

            linCardList.addView(cardView)
        }
    }
}
