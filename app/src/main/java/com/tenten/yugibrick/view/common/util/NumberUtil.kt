package com.tenten.yugibrick.view.common.util

import java.text.DecimalFormat

object NumberUtil {

    @Suppress("MagicNumber")
    fun getOrdinalNumber(n: Int): String {
        return when (n) {
            1 -> "1st"
            2 -> "2nd"
            3 -> "3rd"
            else -> "${n}th"
        }
    }

    /**
     * Example: (isDecimalRequired = true)
     * d = 12345
     * returns 12,345.00
     *
     * d = 12345.12345
     * returns 12,345.12
     *
     * ==================================================
     * Example: (isDecimalRequired = false)
     * d = 12345
     * returns 12,345 (notice that there's no decimal since it's zero)
     *
     * d = 12345.12345
     * returns 12,345.12
     *
     * @param d float to format
     * @param zeroCount number decimal places
     * @param isDecimalRequired true if it will put decimal even zero,
     * false will remove the last decimal(s) if zero.
     */
    fun formatDecimal(d: Float? = 0f, zeroCount: Int, isDecimalRequired: Boolean = true): String {
        val zeros = StringBuilder()

        for (i in 0 until zeroCount) {
            zeros.append("0")
        }

        var pattern = "#,##0"

        if (zeros.isNotEmpty()) {
            pattern += ".$zeros"
        }

        val numberFormat = DecimalFormat(pattern)

        var formattedNumber = if (d != null) numberFormat.format(d) else numberFormat.format(0)

        if (!isDecimalRequired) {
            for (i in formattedNumber.length downTo formattedNumber.length - zeroCount) {
                val number = formattedNumber[i - 1]

                if (number == '0' || number == '.') {
                    formattedNumber = formattedNumber.substring(0, formattedNumber.length - 1)
                } else {
                    break
                }
            }
        }

        return formattedNumber
    }

    fun currencyFormat(amount: String): String? {
        val formatter = DecimalFormat("###,###,##0.00")
        return if (amount != null) {
            formatter.format(amount.toDouble())
        } else {
            null
        }
    }

    fun formatDecimal(d: Double? = 0.0, zeroCount: Int): String {
        return formatDecimal(d?.toFloat(), zeroCount)
    }
}
