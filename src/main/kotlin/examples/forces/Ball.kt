package examples.forces

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import math.Vector2D
import math.add
import math.divide
import math.magSq
import math.multiply
import math.normalize
import math.setMag
import math.toOffSet
import kotlin.math.sqrt

/**
 * Represents a simple ball object
 */
data class Ball(
    var x: Float,
    var y: Float,
    var m: Float,
    var playgroundSize: Size
) {

    val height = playgroundSize.height
    val width = playgroundSize.width

    // Position of ball
    val position = Vector2D(x, y)

    // create a random unit velocity vector for ball
    val velocity = Vector2D(0f, 0f)
    val acceleration = Vector2D(0f, 0f)

    val mass = m

    // Create the radius of the object based on the mass. The more the mass, the more the size of the ball
    val radius = sqrt(mass) * 10
    val ballOvalWidth = radius * 2
    val ballOvalHeight = radius * 2

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
     *
     * If [force = mass * acceleration] and considering the ball has some mass,
     * acceleration = force / mass
     * Thus, more the mass [or consider it as weight force] of an object, lesser will it move.
     */
    fun applyForce(force: Vector2D) {
        val f = force.divide(mass)
        acceleration.add(f)
    }

    /**
     * Friction force which is applied when body comes in contact with a surface
     * The magnitude of friction force according to formula is F = (-1) * `mu` * N * (->)velocity_unit_Vector
     * mu - a greek letter mu stands for friction coefficient and N is the constant based on the mass of the body
     */
    fun friction() {
        val diff = height - (position.y + ballOvalHeight)
        if (diff < 1) {

            // Direction of the friction is opposite
            val friction = velocity.copy()
            friction.normalize()
            friction.multiply(-1f)

            // Magnitude of friction
            val normal = mass
            val mu = 2f
            friction.setMag(mu * normal)

            // Apply the friction force
            applyForce(friction)

            // If we consider this idea - eventually it's reducing the velocity of body so
            // a shortcut to friction could be just remove some percent of velocity

            // velocity.multiply(0.95f)
        }
    }

    /**
     * Drag force - https://en.wikipedia.org/wiki/Drag_(physics)
     * Drag force is a force due to air, or any fluid substance resisting on a body in a medium
     *
     */
    fun drag() {
        // Direction of the drag
        val drag = velocity.copy()
        drag.normalize()
        drag.multiply(-1f)

        // Coefficient of drag - depends on medium
        val coefficientOfDrag = 0.3f

        // magSq() returns magnitude square
        val speedSq = velocity.magSq()
        // set the magnitude
        drag.setMag(coefficientOfDrag * speedSq)
        // apply the drag force
        applyForce(drag)
    }

    /**
     * Calculating the edges so that ball doesn't bounce off the window
     */
    fun edges() {
        if (position.y >= height - ballOvalHeight) {
            position.y = height - ballOvalHeight
            velocity.y *= -1
        }
        if (position.x >= width - ballOvalWidth) {
            position.x = width - ballOvalWidth
            velocity.x *= -1
        } else if (position.x <= 0) {
            position.x = 1f
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
        acceleration.multiply(0f)
    }

    // this draws a circle
    fun render(drawScope: DrawScope) {
        drawScope.drawOval(Color.White, position.toOffSet(), Size(ballOvalWidth, ballOvalHeight), alpha = 0.6f)
    }
}
