package math

import androidx.compose.ui.geometry.Offset
import kotlin.math.acos
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.random.Random

/**
 * Represents a 2-dimensional vector in plane
 *
 * This class can be used to create a vector for any entity with [x] and [y] as
 * x and y points in canvas plane. The canvas plane follows cartesian coordinate system but with origin at
 * top left corner and positive x-axis along right direction and positive y-axis along downward direction.
 *
 * For ex: Here representation of position vector in canvas plane could be given as -
 * <code>
 *     val position = Vector2D(25f, 25f)
 * </code>
 */
data class Vector2D(var x: Float = 0f, var y: Float = 0f) {

    /**
     * Equals method to check the equality between two vectors
     */
    override fun equals(other: Any?): Boolean {
        return other is Vector2D && other.x == this.x && other.y == this.y
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        return result
    }

    companion object {
        /**
         * Creates a vector from an [angle] and [length]
         *
         * Given any angle in radians and length/magnitude of a vector, it'll create a vector with
         * it's x-component and y-component
         *
         * @param angle Angle in radians
         * @param length length in float
         *
         * @return [Vector2D] vector for given angle and with given magnitude
         */
        fun fromAnAngle(angle: Float, length: Float = 1f): Vector2D {
            return Vector2D(length * cos(angle), length * sin(angle))
        }

        /**
         * Creates a random vector (unit vector)
         * Can be used to create a random unit vector along any direction
         * @return a random unit [Vector2D]
         */
        fun randomVector(): Vector2D {
            return fromAnAngle((Random.nextFloat() * PI * 2).toFloat())
        }
    }
}

/*Below are few simple vector algebra methods*/

fun Vector2D.add(other: Vector2D): Vector2D {
    this.x += other.x
    this.y += other.y
    return this
}

operator fun Vector2D.plusAssign(other: Vector2D) {
    this.add(other)
}

/**
 * To add a vector to [this] vector and multiplies the [other] vector with [scalar]
 */
fun Vector2D.addWithScalarMultiply(other: Vector2D, scalar: Float): Vector2D {
    this.x += other.x * scalar
    this.y += other.y * scalar
    return this
}

/**
 * To subtract a vector from [this] vector
 */
fun Vector2D.sub(other: Vector2D): Vector2D {
    this.x -= other.x
    this.y -= other.y
    return this
}

operator fun Vector2D.minusAssign(other: Vector2D) {
    this.sub(other)
}

/**
 * To multiply [this] vector with [x] factor and [y] factor
 */
fun Vector2D.multiply(x: Float, y: Float): Vector2D {
    this.x *= x
    this.y *= y
    return this
}

/**
 * To multiply [this] vector with single [factor]
 * See also [scalarMultiply]
 */
fun Vector2D.multiply(factor: Float): Vector2D {
    return multiply(factor, factor)
}

operator fun Vector2D.timesAssign(factor: Float) {
    this.multiply(factor)
}

/**
 * To divide [this] vector by [x] and [y] values
 */
fun Vector2D.div(x: Float, y: Float): Vector2D {
    this.x /= x
    this.y /= y
    return this
}

/**
 * To divide [this] vector by single [factor]
 */
fun Vector2D.divide(factor: Float): Vector2D {
    return div(factor, factor)
}

operator fun Vector2D.divAssign(factor: Float) {
    this.divide(factor)
}

/**
 * Calculates the magnitude (length) of the vector and returns the result asa float
 */
fun Vector2D.mag(): Float = sqrt(this.magSq())

/**
 * Calculates the squared magnitude of [this] vector and returns the result as a float
 */
fun Vector2D.magSq(): Float = this.x * this.x + this.y * this.y

/**
 * Calculates the dot product of two vectors
 */
fun Vector2D.dot(other: Vector2D): Float = this.x * other.x + this.y * other.y

/**
 * Calculates Euclidean distance between two vectors
 */
fun Vector2D.distance(other: Vector2D): Float = this.sub(other).mag()

/**
 * Normalizes the vector to length 1 - making unit vector
 *
 * @return [Vector2D]
 */
fun Vector2D.normalize(): Vector2D {
    val len = this.mag()
    if (len != 0f) {
        this.scalarMultiply(1 / len)
    }
    return this
}

/**
 * Set the magnitude of this vector to the value used for the <b>n</b>
 * parameter.
 *
 * @param n the new length of the vector
 */
fun Vector2D.setMag(n: Float): Vector2D {
    return this.normalize().multiply(n)
}

/**
 * Limit the magnitude of this vector to the value used for the <b>max</b>
 * parameter.
 * <code>
 * val v = Vector2D(10, 20);
 * // v has components [10.0, 20.0]
 * v.limit(5);
 * // v's components are set to
 * // [2.2271771, 4.4543543 ]
 * </code>
 */
fun Vector2D.limit(max: Float): Vector2D {
    val magSq = this.magSq()
    if (magSq > max * max) {
        val norm = sqrt(magSq)
        this.div(norm, norm).scalarMultiply(max)
    }
    return this
}

/**
 * Calculate the angle of rotation for this vector(only 2D vectors).
 */
fun Vector2D.heading(): Float {
    return atan2(this.y, this.x).fromRadians()
}

/**
 * Rotate the vector to a specific angle, magnitude remains the same
 * @param angle - Angle in radians
 */
fun Vector2D.setHeading(angle: Float): Vector2D {
    val mag = this.mag()
    this.x = mag * cos(angle)
    this.y = mag * sin(angle)
    return this
}

/**
 * Rotates [this] vector by given [angle]
 */
fun Vector2D.rotate(angle: Float): Vector2D {
    val newHeading = (this.heading() + angle).toRadians()
    val mag = this.mag()
    this.x = cos(newHeading) * mag
    this.y = sin(newHeading) * mag
    return this
}

/**
 * Sets [this] vector as [other] vector
 */
fun Vector2D.set(other: Vector2D): Vector2D {
    this.x = other.x
    this.y = other.y
    return this
}

/**
 * Returns angle between [this] vector and [other] vector
 *
 * @return Angle in radians
 */
fun Vector2D.angleBetween(other: Vector2D): Float {
    val dotmag = this.dot(other) / (this.mag() * other.mag())
    val angle = acos(min(1f, max(-1f, dotmag)).toDouble()).toFloat()
    // angle = angle * Math.signum(this.c)
    return angle.toRadians()
}

/**
 * Linear interpolate the vector to another vector
 * @param x the x component
 * @param y the y component
 * @param amt the amount of interpolation; some value between 0.0
 *                     (old vector) and 1.0 (new vector). 0.9 is very near
 *                      the new vector. 0.5 is halfway in between.
 */
fun Vector2D.lerp(x: Float, y: Float, amt: Float): Vector2D {
    this.x += (x - this.x) * amt
    this.y += (y - this.y) * amt
    return this
}

/**
 * Reflect the incoming vector about a normal to a line in 2D, or about a normal to a plane in 3D
 * This method acts on the vector directly
 * @param surfaceNormal the [Vector2D] to reflect about, will be normalized by this method
 */
fun Vector2D.reflect(surfaceNormal: Vector2D): Vector2D {
    surfaceNormal.normalize()
    return this.sub(surfaceNormal.scalarMultiply(2 * this.dot(surfaceNormal)))
}

fun Vector2D.scalarMultiply(scalar: Float): Vector2D {
    this.x *= scalar
    this.y *= scalar
    return this
}

/**
 * Increments [this] vector by [factor]
 */
operator fun Vector2D.inc(factor: Float): Vector2D {
    this.x += factor
    this.y += factor
    return this
}

operator fun Vector2D.dec(factor: Float): Vector2D {
    this.x -= factor
    this.y -= factor
    return this
}

/**
 * Converts a [this] [Vector2D] into [Offset] value
 *
 * @return [Offset] with x and y value as x and y component of vector
 */
fun Vector2D.toOffSet(): Offset {
    return Offset(this.x, this.y)
}
