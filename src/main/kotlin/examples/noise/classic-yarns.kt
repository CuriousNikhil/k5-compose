package examples.noise

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import k5
import math.noise1D

fun showYarns() = k5 {

    noLoop()
    show(modifier = Modifier.fillMaxSize().background(Color(0xFFFFA500))) {
        it.apply {
            var offset = 0.0
            for (i in 0 until 3000) {
                println("$dimensFloat")
                println("size in canvas ${it.size}")
                val x0 = 2 * dimensFloat.width * noise1D(offset + 15)
                val x1 = 2 * dimensFloat.width * noise1D(offset + 25)
                val x2 = 2 * dimensFloat.width * noise1D(offset + 35)
                val x3 = 2 * dimensFloat.width * noise1D(offset + 45)
                val y0 = 2 * dimensFloat.height * noise1D(offset + 55)
                val y1 = 2 * dimensFloat.height * noise1D(offset + 65)
                val y2 = 2 * dimensFloat.height * noise1D(offset + 75)
                val y3 = 2 * dimensFloat.height * noise1D(offset + 85)
                val path = Path()
                path.moveTo(x0.toFloat(), y0.toFloat())
                path.cubicTo(
                    x1.toFloat(), y1.toFloat(),
                    x2.toFloat(), y2.toFloat(),
                    x3.toFloat(), y3.toFloat()
                )
                drawPath(path, Color.Black, alpha = 0.5f, style = Stroke(width = 0.3f))
                offset += 0.002
            }
        }
    }
}
