package examples.vectors

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import k5
import math.Vector2D
import math.multiply
import math.plusAssign
import math.setMag
import math.toOffSet
import kotlin.random.Random

/**
 * Simple random walker example
 *
 * If you want to check out the math and how it works,
 * I would recommend watching Nature of code playlist by Daniel Shiffman
 * https://youtube.com/playlist?list=PLRqwX-V7Uu6ZV4yEcW3uDwOgGXKUUsPOM
 *
 * This is just a random walker which walks along 4 directions
 * left, right, up, down
 */
fun simpleRandomWalk() = k5 {

    var x = 400f
    var y = 400f
    val points = mutableListOf(Offset(x, y))

    show(modifier = Modifier.fillMaxSize().background(Color.Black)) { drawScope ->
        val r = Random.nextInt(0, 5)
        when (r) {
            0 -> x += 5
            1 -> x -= 5
            2 -> y += 5
            4 -> y -= 5
        }

        points.add(Offset(x, y))
        drawScope.drawPoints(
            points, pointMode = PointMode.Lines, Color.Yellow,
            strokeWidth = 8f,
            cap = StrokeCap.Square
        )
    }
}

/**
 * Levy Flight simulation [Wiki](https://en.wikipedia.org/wiki/L%C3%A9vy_flight)
 */
fun levyFlightWalker() = k5 {

    // create a position vector
    val position = Vector2D(400f, 400f)
    // add position vector to points
    val points = mutableListOf(position.toOffSet())

    show { scope ->
        // create a random vector
        val step = Vector2D.randomVector()
        // scalar multiply it by some random factor

        val r = Random.nextInt(100)

        if (r < 1) {
            // out of 100 there's 1% chance of step being miltiplied by a random value between 1 -20
            // This will make walker jump
            step.multiply(Random.nextInt(25, 100).toFloat())
        } else {
            // we don't want to show the jumping of walker randomly.
            // Set the magnitude of the step size some constant
            step.setMag(2f)
        }
        /* If you want to make walker jump randomly,
        you can just use any random scalar to multiply with step vector above^*/

        // add the step vector to position vector
        position += step
        // add new position to points
        points.add(position.toOffSet())

        // draw all the points as a single line
        scope.drawPoints(
            points, pointMode = PointMode.Polygon, Color.White,
            strokeWidth = 5f,
            cap = StrokeCap.Square
        )

        // Keeping the size of points fixed, you can remove this if you want to keep the k5 drawing the lines
        // for all points
        if (points.size > 200) {
            points.removeFirst()
        }
    }
}
