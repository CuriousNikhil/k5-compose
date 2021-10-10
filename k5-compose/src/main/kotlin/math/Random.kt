package math

import kotlin.math.ln
import kotlin.math.sqrt
import kotlin.random.Random

// Set to values from http://en.wikipedia.org/wiki/Numerical_Recipes
// m is basically chosen to be large (as it is the max period)
// and for its relationships to a and c
internal const val m = 4294967296

// a - 1 should be divisible by m's prime factors
internal const val a = 1664525

// c and m should be co-prime
internal const val c = 1013904223
var y2 = 0f

internal val stateValueMap = mutableMapOf<String, Long>()

const val randomStateKey = "_lcg_random_state"

internal var gaussianPrevious = false

fun lcg(state: String): Long {
    val stateVal = stateValueMap[state] ?: 0
    val newVal = (a * stateVal + c) % m
    stateValueMap[state] = newVal
    return newVal / m
}

fun lcgSetSeed(state: String, value: Int?) {
    stateValueMap[state] = (value ?: (Random.nextFloat() * m)).toLong()
}

fun randomSeed(seed: Int) {
    lcgSetSeed(randomStateKey, seed)
    gaussianPrevious = false
}

fun k5Random(min: Int, max: Int): Float {
    var minimum = min
    var maximum = max
    val rand = if (stateValueMap.isNotEmpty()) {
        lcg(randomStateKey)
    } else {
        Random.nextFloat()
    }
    if (min > max) {
        minimum = max
        maximum = min
    }
    return rand.toFloat() * (maximum - minimum) + min
}

fun randomGaussian(mean: Float, standardDeviation: Float = 1f): Float {
    var y1 = 0f
    var x1 = 0f
    var x2 = 0f
    var w = 0f

    if (gaussianPrevious) {
        y1 = y2
        gaussianPrevious = false
    } else {
        do {
            x1 = k5Random(0, 2) - 1
            x2 = k5Random(0, 2) - 1
            w = (x1 * x1 + x2 * x2)
        } while (w >= 1)
        w = sqrt(-2 * ln(w) / w)
        y1 = x1 * w
        y2 = x2 * w
        gaussianPrevious = true
    }
    return y1 * standardDeviation + m
}
