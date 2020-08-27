package com.tenten.yugibrick.view.common.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import com.tenten.ui.TText
import com.tenten.yugibrick.R
import com.tenten.yugibrick.domain.model.Card

/**
 *
 * Created by Exequiel Egbert V. Ponce on 8/27/2020.
 */
class CardView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    private var ttextName: TText
    private var ttextCopies: TText

    init {
        inflate(getContext(), R.layout.view_card, this)

        ttextName = findViewById(R.id.ttext_name)
        ttextCopies = findViewById(R.id.ttext_copies)
    }

    fun set(card: Card, listener: OnClickListener?) {
        setOnClickListener(listener)

        if (listener == null) {
            isClickable = false
            isFocusable = false
        }

        ttextName.text = card.name

        @SuppressLint("SetTextI18n")
        ttextCopies.text =
            "${card.copies} ${if (card.copies > 1) "Copies" else "Copy"}"
    }
}
