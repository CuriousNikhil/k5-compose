package examples.particles

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import k5
import math.Vector2D
import math.distance
import math.plusAssign
import math.toOffSet
import kotlin.random.Random

class Particle(val dimension: Size) {

    val position = Vector2D(
        Random.nextInt(dimension.width.toInt()).toFloat(),
        Random.nextInt(dimension.height.toInt()).toFloat()
    )

    var r = Random.nextInt(1, 8)

    var velocity = Vector2D(
        Random.nextDouble(-2.0, 2.0).toFloat(),
        Random.nextDouble(-1.0, 1.5).toFloat()
    )

    fun createParticle(drawScope: DrawScope) {
        drawScope.drawCircle(Color.White, r.toFloat(), position.toOffSet())
    }

    fun moveParticle() {
        if (position.x < 0f || position.x > dimension.width) {
            velocity.x *= -1
        }
        if (position.y < 0f || position.y > dimension.height) {
            velocity.y *= -1
        }
        position += velocity
    }

    fun joinParticles(drawScope: DrawScope, particles: List<Particle>) {
        particles.forEach {
            val dist = this.position.distance(it.position)
            if (dist < 100f) {
                drawScope.drawLine(Color.Cyan, this.position.toOffSet(), it.position.toOffSet(), alpha = 0.5f)
            }
        }
    }
}

fun particleJs() = k5 {

    val particles = List(50) { Particle(dimensFloat) }

    show { drawScope ->
        for (i in 0 until 50) {
            val it = particles[i]
            it.createParticle(drawScope)
            it.moveParticle()
            it.joinParticles(drawScope, particles.slice(0 until i))
        }
    }
}
