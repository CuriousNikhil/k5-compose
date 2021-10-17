package examples.forces

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import k5
import math.Vector2D
import math.multiply
import kotlin.random.Random

@OptIn(ExperimentalFoundationApi::class)
fun bouncingBall() = k5 {
    val playgroundSize = getPlaygroundDimensions()

    val balls = mutableListOf<Ball>()
    repeat(5) {
        balls.add(
            Ball(
                Random.nextInt(2, playgroundSize.width.toInt()).toFloat(),
                6f,
                Random.nextInt(8, 50).toFloat(),
                playgroundSize
            )
        )
    }

    fun showDrag(ball: Ball) {
        if (ball.position.y >= playgroundSize.height / 2) {
            ball.drag()
        }
    }

    fun showWater(scope: DrawScope) {
        scope.drawRect(
            color = Color.Blue,
            topLeft = Offset(0f, playgroundSize.height / 2),
            size = Size(playgroundSize.width, playgroundSize.height / 2)
        )
    }

    show(
        Modifier.combinedClickable(
            onClick = {
                /**
                 * On mouse click, apply wind force in right direction
                 */
                val wind = Vector2D(50f, 0f)
                balls.forEach { it.applyForce(wind) }
            }
        )
    ) { _, scope ->

        // Apply gravity force every time on ball
        val gravity = Vector2D(0f, 0.2f)

        /* Uncomment to apply drag force
         * This will create some sort of water and air medium. When ball will hit the water
         * it'll experience drag force
         */
        // showWater(scope)

        balls.forEach { ball ->
            val weight = gravity.multiply(ball.mass)
            ball.applyForce(weight)

            /* Uncomment to apply friction force*/
            // ball.friction()

            /* Uncomment to apply drag force
             * This will create some sort of water and air medium. When ball will hit the water
             * it'll experience drag force
             */
            // showDrag(ball)

            ball.update()
            ball.edges()
            ball.render(scope)
        }
    }
}
