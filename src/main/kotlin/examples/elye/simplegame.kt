package examples.elye

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerMoveFilter
import k5
import math.Vector2D
import math.limit
import math.plusAssign
import math.random
import math.set
import math.sub
import math.toOffSet

private const val BALL_RADIUS = 30f

@OptIn(ExperimentalComposeUiApi::class)
fun simpleGame() = k5 {
    var velocity = 5f
    val vehicleY = dimensFloat.height - 250f
    val vehicleStartX = dimensFloat.width / 2
    val vehiclePosition = Vector2D(vehicleStartX, vehicleY)
    val mouseVector = Vector2D(vehicleStartX, vehicleY)
    fun resetStarX() = (40 until dimensInt.width - 40).random().toFloat()
    val starPosition = Vector2D(resetStarX(), 0f)
    fun resetStar() { starPosition.set(Vector2D(resetStarX(), 0f)) }
    fun hitEndPoint() = starPosition.y >= dimensFloat.height
    fun moveStar() { starPosition += Vector2D(0f, velocity) }
    fun collide() =
        (kotlin.math.abs(starPosition.x - vehiclePosition.x) < BALL_RADIUS * 2) &&
            (kotlin.math.abs(starPosition.y - vehiclePosition.y) < BALL_RADIUS * 2)
    show(
        modifier =
        Modifier.pointerMoveFilter(onMove = {
            mouseVector.x = it.x
            mouseVector.y = vehicleY
            false
        })
    ) {
        vehiclePosition += mouseVector.copy().sub(vehiclePosition).limit(5f)

        when {
            hitEndPoint() -> noLoop()
            collide() -> { resetStar(); velocity++ }
            else -> moveStar()
        }
        it.drawCircle(color = Color.Red, radius = BALL_RADIUS, center = starPosition.toOffSet())
        it.drawCircle(color = Color.White, radius = BALL_RADIUS, center = vehiclePosition.toOffSet())
    }
}
