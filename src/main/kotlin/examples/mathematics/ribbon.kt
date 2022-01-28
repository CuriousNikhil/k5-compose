package examples.mathematics

import androidx.compose.foundation.background
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerMoveFilter
import k5
import math.map
import kotlin.math.cos
import kotlin.math.sin

@OptIn(ExperimentalComposeUiApi::class)
fun showRibbon() = k5 {

    var r = 50f
    var t = 0.0
    var dt = 0.0001

    noLoop()
    show(
        modifier = Modifier.background(Color(0xFFE6E6FA)).pointerMoveFilter(onMove = {
            r = map(it.x, 0f, dimensFloat.width, 10f, 100f)
            false
        })
    ) {
        it.apply {

            for (i in 0 until 150000) {
                val delta = 2.0 * r * cos(4.0 * dt * t) + r * cos(1.0 * t)
                val blue = 2.0 * r * sin(t) - r * cos(3.0 * dt * t)
                val color = Color(delta.toInt(), blue.toInt(), 100, 10)

                val x = 2.0 * r * sin(2.0 * dt * t) + r * cos(1.0 * t * dt) + this.size.width / 2
                val y = 2.0 * r * sin(1.0 * dt * t) - r * sin(5.0 * t) + this.size.height / 2
                drawCircle(color, 1.0f, Offset(x.toFloat(), y.toFloat()))

                t += 0.01
                dt += 0.1
            }
        }
    }
}
