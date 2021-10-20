package examples.mathematics

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import k5
import kotlin.math.cos
import kotlin.math.sin

fun parametricEquation() = k5 {

    var theta = 0f

    fun x1(t: Float) = sin(t / 10f) * 125f + sin(t / 20) * 125f + sin(t / 30f) * 125f

    fun y1(t: Float) = cos(t / 10f) * 125f + cos(t / 20f) * 125f + cos(t / 30f) * 125f

    fun x2(t: Float) = sin(t / 15f) * 125f + sin(t / 25f) * 125f + sin(t / 35f) * 125f

    fun y2(t: Float) = cos(t / 15f) * 125f + cos(t / 25f) * 125f + cos(t / 35f) * 125f

    show(Modifier.background(Color.White)) { scope ->
        scope.translate(dimensFloat.width / 2, dimensFloat.height / 2) {
            for (i in 0..100) {
                this.drawLine(
                    Color.Black,
                    Offset(x1(theta + i), y1(theta + i)),
                    Offset(x2(theta + i) + 50, y2(theta + i) + 50)
                )
                theta += 0.006f
            }
        }
    }
}
