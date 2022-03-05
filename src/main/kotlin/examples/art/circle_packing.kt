package examples.art

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import k5
import math.random
import kotlin.math.sqrt

data class Circle(var x: Float, var y: Float, var radius: Float)

fun showCirclePacking() = k5 {

    val colors =
        listOf(Color.Gray, Color.Red, Color.Blue, Color.Green, Color.Yellow, Color.Cyan, Color.Magenta, Color.White)
    val circles = mutableListOf<Circle>()
    val minRadius = 10
    val maxRadius = 300
    val totalCircles = 300
    val createCircleAttempts = 500

    fun doesCircleHaveCollision(circle: Circle): Boolean {
        circles.forEach { other ->
            val a = circle.radius + other.radius
            val x = circle.x - other.x
            val y = circle.y - other.y
            if (a >= sqrt(((x * x) + (y * y)))) {
                return true
            }
        }

        if (circle.x + circle.radius >= dimensFloat.width ||
            circle.x - circle.radius <= 0
        ) {
            return true
        }

        if (circle.y + circle.radius >= dimensFloat.height ||
            circle.y - circle.radius <= 0
        ) {
            return true
        }

        return false
    }

    fun createAndDrawCircle(drawScope: DrawScope) {

        lateinit var newCircle: Circle
        var circleSafeToDraw = false

        for (tries in 0 until createCircleAttempts) {
            newCircle =
                Circle(
                    x = (5f..dimensFloat.width).random(),
                    y = (5f..dimensFloat.height).random(),
                    radius = minRadius.toFloat()
                )

            if (doesCircleHaveCollision(newCircle)) {
                continue
            } else {
                circleSafeToDraw = true
                break
            }
        }

        if (circleSafeToDraw.not()) {
            return
        }

        for (radiusSize in minRadius until maxRadius) {
            newCircle.radius = radiusSize.toFloat()
            if (doesCircleHaveCollision(newCircle)) {
                newCircle.radius--
                break
            }
        }

        circles.add(newCircle)
        drawScope.drawCircle(
            Brush.linearGradient(colors),
            newCircle.radius,
            Offset(newCircle.x, newCircle.y),
            style = Stroke(width = 2f)
        )
    }

    noLoop()
    show {
        for (i in 1..totalCircles) {
            createAndDrawCircle(it)
        }
    }
}
