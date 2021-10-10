package examples.forces

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import k5
import math.Vector2D
import math.add
import math.set
import math.toOffSet

data class Ball(
    var x: Float,
    var y: Float
) {

    val height = 744f
    val width = 800f

    // Position of ball
    val position = Vector2D(x, y)

    // create a random unit velocity vector for ball
    val velocity = Vector2D(0f, 0f)
    val acceleration = Vector2D(0f, 0f)
    val radius = 20f

    /**
     * Applies the force over an object
     *
     * The application of force is basically euler's integration. Where instead of calculating derivatives of position to get velocity,
     * the velocity derivative to get acceleration and so on..
     * Euler's way is the opposite way of calculating position by integrating velocity, velocity by integrating acceleration and so on.
     * Thus this way you are adding all the vectors to position one by one
     * This is easy to understand but not a perfect integration for realy physics simulation
     *
     * Force = Mass * Acceleration
     * But if Mass is unit, the Force = Acceleration
     *
     * The net force being applied on the body is the sum of all forces. That's why we are adding all the forces to acceleration
     */
    fun applyForce(force: Vector2D) {
        acceleration.add(force)
    }

    /**
     * Calculating the edges so that ball doesn't bounce off the window
     */
    fun edges() {
        if (position.y >= height) {
            position.y = height - radius
            velocity.y *= -1
        }
        if (position.x >= width) {
            position.x = width - radius
            velocity.x *= -1
        } else if (position.x <= radius) {
            position.x = radius
            velocity.x *= -1
        }
    }

    fun update() {
        // Add acceleration to velocity
        velocity.add(acceleration)

        // finally, add velocity to position
        position.add(velocity)

        // set the acceleration to zero, because at a given point in time we want to uniformly apply forces
        // acting on a body
        acceleration.set(Vector2D(0f, 0f))
    }

    // this draws a circle
    fun render(drawScope: DrawScope) {
        drawScope.drawOval(Color.White, position.toOffSet(), Size(radius * 2, radius * 2))
    }
}

@ExperimentalFoundationApi
fun bouncingBall() = k5 {
    val ball = Ball(400f, 400f)

    show(
        Modifier.combinedClickable(
            onClick = {
                /**
                 * On mouse click, apply wind force in right direction
                 */
                val wind = Vector2D(10f, 0f)
                ball.applyForce(wind)
            }
        )
    ) { _, scope ->
        // Apply gravity force every time on ball
        val gravity = Vector2D(0f, 2f)
        ball.applyForce(gravity)

        ball.update()
        ball.edges()
        ball.render(scope)
    }
}
