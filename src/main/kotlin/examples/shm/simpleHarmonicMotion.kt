package examples.shm

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import k5
import math.TWO_PI
import math.map
import kotlin.math.sin

fun simulateSineWave() = k5 {

    val angles = mutableListOf<Float>()
    val r = 6
    val total = dimensInt.width / (r * 2)

    repeat(total) {
        angles.add(map(it.toFloat(), 0f, total.toFloat(), 0f, 2 * TWO_PI.toFloat()))
    }

    show { drawScope ->
        drawScope.translate(500f, 500f) {
            for (i in 0 until angles.size) {
                val y = map(sin(angles[i]), -1f, 1f, -400f, 400f)
                val x = map(i.toFloat(), 0f, angles.size.toFloat(), -400f, 400f)
                this.drawCircle(Color.Yellow, r.toFloat(), Offset(x, y))
                angles[i] += 0.02f
            }
        }
    }
}
