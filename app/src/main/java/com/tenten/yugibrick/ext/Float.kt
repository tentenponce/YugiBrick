package com.tenten.yugibrick.ext

import kotlin.math.round

/**
 *
 * Created by Exequiel Egbert V. Ponce on 8/27/2020.
 */
fun Float.round(decimals: Int): Float {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return (round(this * multiplier) / multiplier).toFloat()
}
