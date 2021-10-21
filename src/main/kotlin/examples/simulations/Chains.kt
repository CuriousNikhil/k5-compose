package examples.simulations

import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerMoveFilter
import k5
import math.Vector2D

val gravity = 5f
val mass = 2f

// TODO: WIP
class Spring(
    var x: Float,
    var y: Float,
) {

    var vx = 0f // vx velocity on x
    var vy = 0f // vy velocity on y

    val radius = 60f
    val stiffness = 0.2f
    val damping = 0.3f

    fun update(targetX: Float, targetY: Float) {
        val force = (targetX - x) * stiffness
        val ax = force / mass
        vx = damping * (vx + ax)
        x += vx

        var forceY = (targetY - y) * stiffness
        forceY += gravity
        val ay = force / mass
        vy = damping * (vy + ay)
        y += vy
    }

    fun render(nx: Float, ny: Float, scope: DrawScope) {
        scope.drawLine(Color.White, Offset(x, y), Offset(nx, ny))
        scope.drawCircle(Color.White, radius, Offset(x, y), alpha = 0.4f)
    }
}

fun chainLoop() = k5 {
    val dimens = dimensFloat
    val l1 = Spring(0f, dimens.width / 2)
    val l2 = Spring(0f, dimens.width / 2)

    val mouse = Vector2D(0f, 0f)
    show(
        Modifier.pointerMoveFilter(
            onMove = {
                println("${it.x} ${it.y}")
                mouse.x = it.x
                mouse.y = it.y
                true
            }
        )
    ) { scope ->
        l1.update(mouse.x, mouse.y)
        l1.render(mouse.x, mouse.y, scope)
        // l2.update(l1.x, l1.y)
        // l2.render(l1.x, l1.y, scope)
    }
}
