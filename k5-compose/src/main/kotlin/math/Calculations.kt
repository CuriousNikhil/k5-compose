package math

import kotlin.math.max
import kotlin.math.min

/**
 * Constrains a value between a minimum and maximum value.
 *
 * @method constrain
 * @param n   number to constrain
 * @param low  minimum limit
 * @param high maximum limit
 */
fun constrain(n: Float, low: Float, high: Float): Float {
    return max(min(n, high), low)
}

/**
 * Re-maps a number from one range to another.
 *
 * @param value  the incoming value to be converted
 * @param start1 lower bound of the value's current range
 * @param stop1  upper bound of the value's current range
 * @param start2 lower bound of the value's target range
 * @param stop2  upper bound of the value's target range
 * @param withinBounds constrain the value to the newly mapped range
 * @return remapped number
 */
fun map(n: Float, start1: Float, stop1: Float, start2: Float, stop2: Float, withBounds: Boolean = true): Float {
    val newval = (n - start1) / (stop1 - start1) * (stop2 - start2) + start2
    return if (!withBounds) {
        newval
    } else {
        if (start2 < stop2) {
            constrain(newval, start2, stop2)
        } else {
            constrain(newval, stop2, start2)
        }
    }
}
