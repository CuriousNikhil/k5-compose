package examples.kinematics

import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerMoveFilter
import k5
import math.Vector2D
import math.add
import math.limit
import math.scalarMultiply
import math.setMag
import math.sub
import math.toOffSet
import kotlin.random.Random

/**
 * Simple mover class
 *
 * This mover will represent a circle which will move according to the
 * velocity and acceleration that we'll assign to it.
 *
 * We'll also use mouse pointer to alter the velocity and acceleration of mover
 *
 * You can play with it by tweaking any of the values as you like :p
 */
data class Mover(
    var x: Float,
    var y: Float
) {
    // Position of mover
    val position = Vector2D(x, y)

    // create a random unit velocity vector for mover
    val velocity = Vector2D.randomVector()

    init {
        // scaling the velocity by some constant factor
        velocity.scalarMultiply(Random.nextInt(3).toFloat())
    }

    fun update(mouse: Vector2D) {
        // create acceleration vector by subtracting position from mouse pointer position
        val acceleration = mouse.sub(position)
        // setting magnitude of acceleration to constant value
        acceleration.setMag(1f)

        /**
         * In order to understand how velocity, acceleration applies in 2d physics engine
         * I would recommend watching the Daniel Shiffman's Nature of code videos with p5.js
         * https://www.youtube.com/playlist?list=PLRqwX-V7Uu6ZV4yEcW3uDwOgGXKUUsPOM
         */

        // Add acceleration to velocity
        velocity.add(acceleration)
        // set limit to velocity some constant value, so it won't bounce off
        velocity.limit(5f)
        // finally, add velocity to position
        position.add(velocity)
    }

    // this draws a circle
    fun render(drawScope: DrawScope) {
        drawScope.drawOval(Color.White, position.toOffSet(), Size(30f, 30f))
    }
}

fun simpleMover() = k5 {

    // Create a mover object with initial position
    val mover = Mover(400f, 400f)

    // create a mouse vector for mouse pointer position
    val mouse = Vector2D(0f, 0f)

    show(
        Modifier.pointerMoveFilter(
            // get mouse pointer location/position
            onMove = {
                mouse.x = it.x
                mouse.y = it.y
                false
            }
        )
    ) { _, scope ->
        // update the mover
        mover.update(mouse)
        // render the mover
        mover.render(scope)
    }
}
