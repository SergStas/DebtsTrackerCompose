package util.extensions

import kotlin.math.pow

fun Double.format(digits: Int) =
    when {
        digits == 0 -> toInt().toString()
        digits < 0 -> toString()
        else -> ((this * 10.0.pow(digits)).toInt() / 100.0).toString()
    }
