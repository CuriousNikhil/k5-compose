package examples.particles

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ExperimentalGraphicsApi
import androidx.compose.ui.graphics.drawscope.DrawScope
import k5
import math.Vector2D
import math.map
import math.multiply
import math.plusAssign
import math.random
import math.toOffSet
import kotlin.random.Random

val gravity = Vector2D(0f, 0.2f)

data class FireWorkParticle(
    val x: Float,
    val y: Float,
    val hu: Float,
    val isFireWork: Boolean
) {
    val position = Vector2D(x, y)
    var lifespan = 255f
    val acceleration = Vector2D(0f, 0f)
    val velocity: Vector2D

    init {
        if (isFireWork) {
            velocity = Vector2D(0f, (-15f..-18f).random())
        } else {
            velocity = Vector2D.randomVector()
            velocity.multiply((2f..10f).random())
        }
    }

    fun applyForece(force: Vector2D) {
        acceleration += force
    }

    fun update() {
        if (!isFireWork) {
            velocity.multiply(0.9f)
            lifespan -= 4
        }
        velocity += acceleration
        position += velocity
        acceleration.multiply(0f)
    }

    fun isDone() = lifespan < 0

    @ExperimentalGraphicsApi
    fun show(drawScope: DrawScope) {
        if (!isFireWork) {
            val alpha = map(lifespan, 255f, 1f, 1f, 0f)
            drawScope.drawCircle(Color.hsv(hu, 1f, 1f), 5f, position.toOffSet(), alpha = alpha)
        } else {
            drawScope.drawRect(Color.hsv(hu, 1f, 1f), position.toOffSet(), Size(5f, 10f))
            val offset = Offset(position.x + 2f, position.y)
            drawScope.drawCircle(Color.hsv(hu, 1f, 1f), 5f, offset)
        }
    }
}

data class FireWork(val dimens: Size) {
    val hue = (0f..360f).random()
    val firework = FireWorkParticle((1f..dimens.width).random(), dimens.height, hue, true)
    var isExploded = false
    val particles = mutableListOf<FireWorkParticle>()

    fun isDone() = isExploded && particles.isEmpty()

    fun update() {
        if (!isExploded) {
            firework.applyForece(gravity)
            firework.update()

            if (firework.velocity.y >= 0f) {
                isExploded = true
                explode()
            }
        }

        if (particles.isNotEmpty()) {
            for (i in particles.size - 1 downTo 0) {
                particles[i].applyForece(gravity)
                particles[i].update()

                particles.removeAll { it.isDone() }
            }
        }
    }

    fun explode() {
        repeat(100) {
            val p = FireWorkParticle(firework.position.x, firework.position.y, hue, false)
            particles.add(p)
        }
    }

    @ExperimentalGraphicsApi
    fun show(drawScope: DrawScope) {
        if (!isExploded) {
            firework.show(drawScope)
        }
        particles.forEach {
            it.show(drawScope)
        }
    }
}

@ExperimentalGraphicsApi
fun showFireWorks() = k5 {

    val fireworks = MutableList(10) { FireWork(dimensFloat) }

    show { drawScope ->

        if (Random.nextFloat() < 0.04f) {
            fireworks.add(FireWork(dimensFloat))
        }

        for (i in (fireworks.size - 1) downTo 0) {
            fireworks[i].update()
            fireworks[i].show(drawScope)

            fireworks.removeAll { it.isDone() }
        }
    }
}
