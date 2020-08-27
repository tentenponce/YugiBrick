package com.tenten.yugibrick.domain.model

/**
 *
 * Created by Exequiel Egbert V. Ponce on 8/28/2020.
 */
data class DeckCalculator(
    val combos: List<Combo>,
    val deckSize: Int,
    val handSize: Int
)
