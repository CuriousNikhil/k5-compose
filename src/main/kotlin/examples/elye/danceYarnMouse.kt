package examples.noise

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerMoveFilter
import k5
import math.Vector2D
import math.noise3D

fun danceYarnMouse() = k5 {
    val mouseVector = Vector2D()
    show(modifier = Modifier
        .pointerMoveFilter(onMove = {
            mouseVector.x = it.x
            mouseVector.y = it.y
            false })) {
        it.apply {
            var offset = 0.0
            val m2d = mouseVector.x * 0.002
            val m3d = mouseVector.y * 0.002
            for (i in 1000 until 1200) {
                fun noiseX(variant: Double) = 2 * dimensFloat.width * noise3D(variant, m2d, m3d)
                fun noiseY(variant: Double) = 2 * dimensFloat.height * noise3D(offset + variant, m2d, m3d)
                fun color(variant: Double) =  0xFF - (0xFF * noise3D(variant, m2d, m3d)).toInt()
                val x = FloatArray(3) { index -> noiseX(offset + 5 + index * 10).toFloat() }
                val y = FloatArray(3) { index -> noiseY(offset + 5 + index * 10).toFloat() }
                val path = Path()
                path.moveTo(mouseVector.x, mouseVector.y)
                path.cubicTo(x[0], y[0], x[1], y[1], x[2], y[2])
                val red = color(offset + 35)
                val green = color(offset + 25)
                val blue = color(offset + 15)
                drawPath(path, Color(red, green, blue), style = Stroke(width = 0.3f))
                offset += 0.002
            }
        }
    }
}
