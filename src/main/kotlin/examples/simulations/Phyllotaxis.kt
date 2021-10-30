package examples.simulations

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ExperimentalGraphicsApi
import androidx.compose.ui.graphics.drawscope.translate
import k5
import math.cos
import math.map
import math.sin
import math.toRadians
import kotlin.math.sqrt

@ExperimentalGraphicsApi
fun showPhyllotaxis() = k5 {
    var n = 0
    val c = 6f
    var start = 0

    show {
        it.translate(dimensFloat.width / 2, dimensFloat.height / 2) {
            for (i in 0 until n) {
                val a = i * 137.5f.toRadians()
                val r = c * sqrt(i.toFloat())
                val x = r * a.cos()
                val y = r * a.sin()
                var hu = (start + i * 0.5f).sin()
                hu = map(hu, -1f, 1f, 0f, 360f)
                println(hu)
                this.drawCircle(Color.hsv(hu, 1f, 1f), 4f, Offset(x, y))
            }
        }
        n += 5
        start += 5
    }
}
