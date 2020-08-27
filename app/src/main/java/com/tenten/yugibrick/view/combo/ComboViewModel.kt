package com.tenten.yugibrick.view.combo

import androidx.lifecycle.MutableLiveData
import com.tenten.yugibrick.domain.model.Card
import com.tenten.yugibrick.view.base.BaseViewModel
import com.tenten.yugibrick.view.common.util.SingleLiveEvent
import org.koin.ext.isInt

/**
 *
 * Created by Exequiel Egbert V. Ponce on 8/27/2020.
 */
class ComboViewModel : BaseViewModel() {

    val stateCards = MutableLiveData<List<Card>>()

    val stateShowCardDialog = SingleLiveEvent<Any>()

    val stateInvalidCopies = SingleLiveEvent<Any>()

    fun addCard() {
        stateShowCardDialog.call()
    }

    fun addCard(name: String, copies: String) {
        if (copies.isInt()) {
            val cardToAdd = Card(name, copies.toInt())

            val cardNames = stateCards.value
                ?.map { card -> card.name } ?: listOf()

            if (!cardNames.contains(name)) {
                stateCards.value = stateCards.value
                    ?.toMutableList()
                    ?.apply { add(cardToAdd) }
                    ?: listOf(cardToAdd)
            } else {
                val index = cardNames.indexOf(name)

                stateCards.value = stateCards.value!!
                    .toMutableList()
                    .apply { set(index, cardToAdd) }
            }
        } else {
            stateInvalidCopies.call()
        }
    }

    fun delete(card: Card) {
        stateCards.value = stateCards.value!!
            .toMutableList()
            .apply {
                val index = stateCards.value!!
                    .map { card -> card.name }
                    .indexOf(card.name)

                removeAt(index)
            }
    }
}
