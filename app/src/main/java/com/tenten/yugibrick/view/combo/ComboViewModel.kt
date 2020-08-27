package com.tenten.yugibrick.view.combo

import androidx.lifecycle.MutableLiveData
import com.tenten.yugibrick.domain.interactor.CreateCombo
import com.tenten.yugibrick.domain.model.Card
import com.tenten.yugibrick.domain.model.Combo
import com.tenten.yugibrick.domain.model.ComboCalculator
import com.tenten.yugibrick.view.base.BaseViewModel
import com.tenten.yugibrick.view.common.util.SingleLiveEvent
import io.reactivex.rxkotlin.subscribeBy
import org.koin.ext.isInt

/**
 *
 * Created by Exequiel Egbert V. Ponce on 8/27/2020.
 */
class ComboViewModel(
    private val createCombo: CreateCombo
) : BaseViewModel() {

    val stateCards = MutableLiveData<List<Card>>()

    val stateShowCardDialog = SingleLiveEvent<Any>()

    val stateInvalidCopies = SingleLiveEvent<Any>()

    val stateShowDone = MutableLiveData<Boolean>()

    val stateSubmitCombo = SingleLiveEvent<Combo>()

    private var deckSize: Int = 0
    private var handSize: Int = 0

    fun init(deckSize: Int, handSize: Int) {
        this.deckSize = deckSize
        this.handSize = handSize
    }

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

        hideShowDone()
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

        hideShowDone()
    }

    private fun hideShowDone() {
        stateShowDone.value = (stateCards.value ?: listOf()).isNotEmpty()
    }

    fun submitCombo() {
        createCombo.execute(
            ComboCalculator(
                cards = stateCards.value!!,
                handSize = handSize,
                deckSize = deckSize
            )
        ).doOnSubscribe { disposables.add(it) }
            .subscribeBy(onSuccess = { combo ->
                stateSubmitCombo.value = combo
            }, onError = {
                // TODO: we're very much sure that there's no error as of now...
                // TODO: lol
            })
    }
}
