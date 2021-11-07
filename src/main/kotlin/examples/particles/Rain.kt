package examples.particles

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import k5
import math.PI
import math.TAU
import kotlin.math.tan
import kotlin.random.Random

fun showRain() = k5 {

    val r = mutableListOf<Float>()
    val N = 200
    var t = 0f

    repeat(N) {
        r.add(Random.nextFloat())
    }

    show(modifier = Modifier.background(Color(0xffaec6cf))) {

        t += 0.002f
        for (i in 0 until N) {
            it.drawCircle(
                Color.Cyan,
                center = Offset(
                    x = dimensFloat.width * r[(i + 2) % N],
                    y = (tan(-PI / 2 + TAU * (t + r[i])) * 10 + dimensFloat.width * (1 - r[(i + 1) % N])).toFloat()
                ),
                radius = 10 * r[(i + 3) % N]
            )
        }
    }
}
