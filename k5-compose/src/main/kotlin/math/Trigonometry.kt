package math

import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

/**
 * Set global angle mode DEGREES/RADIANS [AngleMode]
 */
var angleMode = AngleMode.RADIANS

fun Float.tan(): Float {
    return tan(this.toRadians())
}

fun Float.sin(): Float {
    return sin(this.toRadians())
}

fun Float.cos(): Float {
    return cos(this.toRadians())
}

fun Float.atan2(y: Float, x: Float): Float {
    return kotlin.math.atan2(y.toDouble(), x.toDouble()).toFloat().toRadians()
}

fun Float.atan(ratio: Float): Float {
    return kotlin.math.atan(ratio).toRadians()
}

fun Float.asin(ratio: Float): Float {
    return kotlin.math.asin(ratio).toRadians()
}

fun Float.acos(ratio: Float): Float {
    return kotlin.math.acos(ratio).toRadians()
}

/**
 * Converts float to radians
 */
fun Float.toRadians(): Float {
    if (angleMode == AngleMode.DEGREES) {
        return this * DEG_TO_RAD.toFloat()
    }
    return this
}

/**
 * Converts float to degrees
 */
fun Float.toDegrees(): Float {
    if (angleMode == AngleMode.RADIANS) {
        return this * RAD_TO_DEG.toFloat()
    }
    return this
}

/**
 * Converts [this] degrees to radians
 */
fun Float.fromRadians(): Float {
    if (angleMode == AngleMode.RADIANS) {
        return this * RAD_TO_DEG.toFloat()
    }
    return this
}
