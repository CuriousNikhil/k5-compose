package examples.mathematics

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import k5

fun simulateRecursion() = k5 {

    noLoop()

    show {
        it.showCircle(dimensFloat.width / 2, dimensFloat.height / 2, 500f)
    }
}

fun DrawScope.showCircle(x: Float, y: Float, d: Float) {
    this.drawCircle(Color.White, d / 2, Offset(x, y), style = Stroke(5f))
    if (d > 2) {
        val newD = d * 0.5f
        showCircle(x + newD, y, newD)
        showCircle(x - newD, y, newD)
    }
}
