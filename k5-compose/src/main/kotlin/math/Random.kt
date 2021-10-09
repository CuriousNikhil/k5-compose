package math

// Set to values from http://en.wikipedia.org/wiki/Numerical_Recipes
// m is basically chosen to be large (as it is the max period)
// and for its relationships to a and c
internal const val m = 4294967296
// a - 1 should be divisible by m's prime factors
internal const val a = 1664525
// c and m should be co-prime
internal const val c = 1013904223
val y2 = 0

var gaussianPrevious = false

// TODO: Add random gaussian
fun randomGaussian(mean: Float, sd: Float = 1f) {
}
