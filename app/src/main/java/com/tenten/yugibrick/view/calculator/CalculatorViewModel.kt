package com.tenten.yugibrick.view.calculator

import androidx.lifecycle.MutableLiveData
import com.tenten.yugibrick.domain.interactor.CalculateCombos
import com.tenten.yugibrick.domain.interactor.CalculateDeckCombo
import com.tenten.yugibrick.domain.model.Combo
import com.tenten.yugibrick.domain.model.DeckCalculator
import com.tenten.yugibrick.view.base.BaseViewModel
import com.tenten.yugibrick.view.common.util.SingleLiveEvent
import io.reactivex.rxkotlin.subscribeBy
import org.koin.ext.isInt

/**
 *
 * Created by Exequiel Egbert V. Ponce on 8/27/2020.
 */
class CalculatorViewModel(
    private val calculateDeckCombo: CalculateDeckCombo,
    private val calculateCombos: CalculateCombos
) : BaseViewModel() {

    companion object {
        const val DEFAULT_DECK_SIZE = 40
        const val DEFAULT_HAND_SIZE = 5
    }

    val stateCombos = MutableLiveData<List<Combo>>()

    val stateProbability = SingleLiveEvent<Float>()

    val stateAddCombo = SingleLiveEvent<Pair<Int, Int>>()

    private var deckSize = DEFAULT_DECK_SIZE
    private var handSize = DEFAULT_HAND_SIZE

    fun setDeckSize(deckSize: String) {
        if (deckSize.isInt()) {
            this.deckSize = deckSize.toInt()
            reCalculateCombos()
        }
    }

    fun setHandSize(handSize: String) {
        if (handSize.isInt()) {
            this.handSize = handSize.toInt()
            reCalculateCombos()
        }
    }

    fun calculate() {
        val deckCalculator = DeckCalculator(
            combos = stateCombos.value ?: listOf(),
            deckSize = deckSize,
            handSize = handSize
        )

        calculateDeckCombo.execute(deckCalculator)
            .doOnSubscribe { disposables.add(it) }
            .subscribeBy(onSuccess = { probability ->
                stateProbability.value = probability
            })
    }

    fun addCombo() {
        stateAddCombo.value = Pair(deckSize, handSize)
    }

    fun addCombo(combo: Combo) {
        stateCombos.value = stateCombos.value
            ?.toMutableList()
            ?.apply { add(combo) }
            ?: listOf(combo)
    }

    fun deleteCombo(comboID: String) {
        val comboIDs = stateCombos.value!!
            .map { combo -> combo.id }

        stateCombos.value = stateCombos.value!!
            .toMutableList()
            .apply { removeAt(comboIDs.indexOf(comboID)) }
    }

    private fun reCalculateCombos() {
        val combos = stateCombos.value ?: listOf()

        val deckCalculator = DeckCalculator(
            combos = combos,
            handSize = handSize,
            deckSize = this.deckSize
        )

        calculateCombos.execute(deckCalculator)
            .doOnSubscribe { disposables.add(it) }
            .subscribeBy(onSuccess = { updatedCombos ->
                stateCombos.value = updatedCombos
            })
    }
}
