package examples.particles

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
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

// TODO: [WIP] Fix the display of fireworks!
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
            velocity = Vector2D(0f, (-12f..-8f).random())
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

    // @ExperimentalGraphicsApi
    fun show(drawScope: DrawScope) {
        if (!isFireWork) {
            val alpha = map(lifespan, 255f, 1f, 1f, 0f)
            drawScope.drawCircle(Color.Yellow, 5f, position.toOffSet())
        } else {
            drawScope.drawCircle(Color.Yellow, 10f, position.toOffSet())
        }
    }
}

data class FireWork(val dimens: Size) {
    val hue = (0f..360f).random()
    val firework = FireWorkParticle((1f..dimens.width).random(), dimens.height, hue, true)
    var isExploded = false
    val particles = mutableListOf<FireWorkParticle>()

    fun isDone() = isExploded && particles.size == 0

    fun update() {
        if (!isExploded) {
            firework.applyForece(gravity)
            firework.update()

            if (firework.velocity.y >= 0f) {
                isExploded = true
                explode()
            }
        }

        for (i in (particles.size - 1)..0) {
            particles[i].applyForece(gravity)
            particles[i].update()

            if (particles[i].isDone()) {
                particles.removeSlice(1, i)
            }
        }
    }

    fun explode() {
        repeat(100) {
            val p = FireWorkParticle(firework.position.x, firework.position.y, hue, false)
            particles.add(p)
        }
    }

    // @ExperimentalGraphicsApi
    fun show(drawScope: DrawScope) {
        if (!isExploded) {
            firework.show(drawScope)
        }
        particles.forEach {
            it.show(drawScope)
        }
    }
}

// @ExperimentalGraphicsApi
fun showFireWorks() = k5 {

    val fireworks = mutableListOf<FireWork>()
    fireworks.add(FireWork((dimensFloat)))
    fireworks.add(FireWork((dimensFloat)))

    show {

        if (Random.nextFloat() < 0.04) {
            fireworks.add(FireWork((dimensFloat)))
        }

        println(fireworks.size)

        for (i in (fireworks.size - 1)..0) {
            fireworks[i].update()
            fireworks[i].show(it)

            if (fireworks[i].isDone()) {
                fireworks.removeSlice(1, i)
            }
        }
    }
}

fun <E> MutableList<E>.removeSlice(from: Int, end: Int) {
    this.removeAll(this.subList(from, end + 1))
}
