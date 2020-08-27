package com.tenten.yugibrick.domain.model

/**
 *
 * Created by Exequiel Egbert V. Ponce on 8/27/2020.
 */
/**
 * number of card combination to execute combo.
 *
 * Example of a 2 card combination:
 * 3 copies of field spell
 * 3 copies of monster
 */
data class Combo(
    val cards: List<Card>
)
