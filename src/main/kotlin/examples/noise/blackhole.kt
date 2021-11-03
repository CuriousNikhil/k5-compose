package examples.noise

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import k5
import math.AngleMode
import math.angleMode
import math.cos
import math.noise3D
import math.random
import math.sin
import kotlin.math.pow
import kotlin.math.sqrt

fun showBlackHole() = k5 {

    noLoop()
    angleMode = AngleMode.DEGREES

    var circleNumbers = 200
    val circleGap = 0.01f
    var i = 0
    show {

        it.apply {
            while (i <= circleNumbers) {
                val radius = (this.size.width / 10) + i * 0.05f
                val k = (0.5f..1f).random() * sqrt(1.0 * i / circleNumbers).toFloat()
                val noisiness = 400f * (1.0f * i / circleNumbers).pow(2)

                var theta = 0f
                var bx = 0f
                var by = 0f
                while (theta < 361f) {
                    val r1 = theta.cos()
                    val r2 = theta.sin()
                    val r = radius + noise3D(1.0 * k * r1, 1.0 * k * r2, 1.0 * i * circleGap).toFloat() * noisiness

                    val x = this.size.width / 2 + r * theta.cos()
                    val y = this.size.height / 2 + r * theta.sin()

                    if (bx == 0f && by == 0f) {
                        bx = x
                        by = y
                    }

                    drawLine(Color(0xfff8a614), Offset(bx, by), Offset(x, y), 0.4f)
                    bx = x
                    by = y
                    theta += 1.0f
                }
                i++
            }
        }
    }
}
