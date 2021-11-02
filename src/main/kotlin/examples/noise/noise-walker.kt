package examples.noise

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.IntSize
import k5
import math.TWO_PI
import math.Vector2D
import math.k5Random
import math.limit
import math.noise2D
import math.plusAssign
import math.setMag
import math.timesAssign
import math.toOffSet

var inc = 0.1f
var scl = 10

data class Particle(val dimens: IntSize) {

    val position = Vector2D(k5Random(max = dimens.width), k5Random(max = dimens.height))
    val velocity = Vector2D(0f, 0f)
    val acceleration = Vector2D(0f, 0f)
    val maxSpeed = 4f
    val points = mutableListOf<Offset>()
    var skipPoint = false

    init {
        points.add(position.toOffSet())
    }

    fun update() {
        velocity += acceleration
        velocity.limit(maxSpeed)
        position += velocity
        acceleration *= 0f
    }

    fun follow(force: Vector2D) {
        val x = position.x / scl
        val y = position.y / scl
        applyForce(force)
    }

    private fun applyForce(force: Vector2D) {
        acceleration += force
    }

    fun render(drawScope: DrawScope) {
        points.add(position.toOffSet())
        drawScope.drawPoints(points, pointMode = PointMode.Polygon, Color.White)
    }

    fun edges() {
        skipPoint = false
        if (position.x > dimens.width) {
            position.x = 0f
            skipPoint = true
        }
        if (position.x < 0) {
            position.x = dimens.width.toFloat()
            skipPoint = true
        }
        if (position.y > dimens.height) {
            position.y = 0f
            skipPoint = true
        }
        if (position.y < 0) {
            position.y = dimens.height.toFloat()
            skipPoint = true
        }
    }
}

fun simplexNoise() = k5 {

    val particle = Particle(dimensInt)
    var yoff = 0f
    var xoff = 0f
    show { drawScope ->
        val angle = noise2D(x = xoff.toDouble(), y = yoff.toDouble()) * TWO_PI + 4
        val v = Vector2D.fromAnAngle(angle.toFloat())
        v.setMag(1f)
        xoff += inc
        yoff += inc

        particle.apply {
            follow(v)
            update()
            // it.edges()
            render(drawScope)
        }
    }
}
