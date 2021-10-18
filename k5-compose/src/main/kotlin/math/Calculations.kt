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

/**
 * Calculates a number between two numbers at a specific increment. The amt
 * parameter is the amount to interpolate between the two values where 0.0
 * equal to the first point, 0.1 is very near the first point, 0.5 is
 * half-way in between, and 1.0 is equal to the second point. If the
 * value of amt is more than 1.0 or less than 0.0, the number will be
 * calculated accordingly in the ratio of the two given numbers. The lerp
 * function is convenient for creating motion along a straight
 * path and for drawing dotted lines.
 *
 * @method lerp
 * @param start first value
 * @param stop second value
 * @param amt number
 * @return lerped value
 */
fun lerp(start: Float, stop: Float, amt: Float): Float {
    return amt * (stop - start) + start
}

/**
 * Normalizes a number from another range into a value between 0 and 1.
 * Identical to map(value, low, high, 0, 1).
 * Numbers outside of the range are not clamped to 0 and 1, because
 * out-of-range values are often intentional and useful. (See the example above.)
 *
 * @method norm
 * @param n incoming value to be normalized
 * @param start lower bound of the value's current range
 * @param stop  upper bound of the value's current range
 */
fun norm(n: Float, start: Float, stop: Float): Float {
    return map(n, start, stop, 0f, 1f)
}
