package com.tenten.yugibrick.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

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
@Parcelize
data class Combo(
    val id: String,
    val cards: List<Card>,
    val probability: Float
) : Parcelable
