package examples.mathematics

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import k5
import math.AngleMode
import math.PI
import math.angleMode
import kotlin.math.cos
import kotlin.math.sin

fun showCircleLoop() = k5 {

    angleMode = AngleMode.DEGREES
    noLoop()
    val radius = 100f

    show {

        it.apply {
            var theta = 0.0
            var r = radius.toDouble()
            for (i in 0..1000) {
                translate(
                    this.size.width / 2 - r.toFloat() / 2,
                    this.size.height / 2 - r.toFloat() / 2
                ) {
                    val x = radius * cos(Math.toRadians(theta))
                    val y = radius * sin(Math.toRadians(theta * 2))

                    drawOval(
                        color = Color(0x1EFFA500),
                        topLeft = Offset(x.toFloat(), y.toFloat()),
                        size = Size(r.toFloat(), r.toFloat()),
                        style = Stroke(width = 1.0f)
                    )
                }
                r += cos(theta) * sin(theta / 2) + sin(theta) * cos(theta / 2)
                theta += PI / 2
            }
        }
    }
}
