package com.tenten.yugibrick.domain.service

import com.tenten.yugibrick.domain.model.Card
import com.tenten.yugibrick.domain.model.Combo
import com.tenten.yugibrick.domain.model.ComboCalculator
import com.tenten.yugibrick.domain.model.DeckCalculator
import com.tenten.yugibrick.ext.round

/**
 *
 * Created by Exequiel Egbert V. Ponce on 8/27/2020.
 */
class ProbabilityService {

    fun calculate(deckCalculator: DeckCalculator): Float {
        return combosCalculator(
            combos = deckCalculator.combos,
            deckCount = deckCalculator.deckSize,
            startingHand = deckCalculator.handSize
        )
    }

    /**
     * @param combos list of your combos
     * Example:
     *
     * 1-card combo:
     * monster x3
     *
     * 2-card combo:
     * field x3 + monster x3
     * terraforming x1 + monster x3
     *
     * @return the probability of getting atleast 1 of your combo on your opening hand.
     */
    fun combosCalculator(combos: List<Combo>, startingHand: Int, deckCount: Int): Float {
        var productOfNotHappening = 1f

        for (combo in combos) {
            val probabilityOfComboNotHappening =
                1 - comboCalculator(combo.cards, startingHand, deckCount)

            productOfNotHappening *= probabilityOfComboNotHappening
        }

        return (1 - productOfNotHappening).round(2)
    }

    fun comboCalculator(comboCalculator: ComboCalculator): Float {
        return comboCalculator(
            cards = comboCalculator.cards,
            startingHand = comboCalculator.handSize,
            deckCount = comboCalculator.deckSize
        )
    }

    /**
     * @param combo your 1, 2, 3... card combo(s)
     * @param startingHand going first is 5, 2nd is 6
     * @param deckCount your deck count duh
     *
     * @return chance of getting combo on your opening hand
     */
    fun comboCalculator(cards: List<Card>, startingHand: Int, deckCount: Int): Float {
        var remainingStartingHand = startingHand
        var remainingDeckCount = deckCount

        var comboProbability = 1f

        for (card in cards) {
            comboProbability *= probabilityOfDrawingOneCopy(
                remainingStartingHand,
                remainingDeckCount,
                card.copies
            )

            remainingStartingHand -= 1
            remainingDeckCount -= 1
        }

        return comboProbability.round(2)
    }

    fun probabilityOfDrawingOneCopy(
        startingHand: Int,
        deckCount: Int,
        numberOfCopies: Int
    ): Float {
        var productOfProbNotHappening = 1f

        for (i in 1..startingHand) {
            /**
             * Deck count is deducting by 1 every time you draw.
             */
            val remainingDeckCount = deckCount - (i - 1)

            /**
             * number of cards that we are not looking for.
             *
             * Example:
             * assuming we have 40 cards in the deck
             * assuming we have 3 copies that we are looking for
             *
             * 40 - 3 = 37 cards are not we are looking for.
             */
            val numberOfNotWeAreNotLooking = remainingDeckCount - numberOfCopies

            val probabilityOfNotHappening = probability(
                numberOfNotWeAreNotLooking.toFloat(),
                remainingDeckCount.toFloat()
            )

            productOfProbNotHappening *= probabilityOfNotHappening
        }

        return (1f - productOfProbNotHappening).round(2)
    }

    fun probability(numberOfChance: Float, total: Float): Float {
        return (numberOfChance / total).round(2)
    }
}
