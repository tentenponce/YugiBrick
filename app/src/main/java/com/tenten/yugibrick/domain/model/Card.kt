package com.tenten.yugibrick.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 *
 * Created by Exequiel Egbert V. Ponce on 8/27/2020.
 */
/**
 * Example: 3 copies of field spell
 */
@Parcelize
data class Card(
    val name: String,
    val copies: Int
): Parcelable
