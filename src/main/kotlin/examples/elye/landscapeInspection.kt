package examples.elye

import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.input.pointer.pointerMoveFilter
import k5
import math.Vector2D
import math.noise2D

fun landscapeInspection() {
    val loop = 1000
    val slices = 250
    val mouseInsensitivity = 50
    val mouseVector = Vector2D()
    k5 {
        show(
            modifier =
            Modifier.pointerMoveFilter(onMove = {
                mouseVector.x = it.x
                mouseVector.y = it.y
                false
            })
        ) {
            for (offset in 0 until slices) {
                for (x in 0 until loop) {
                    val noiseInputX = x * 0.002
                    val noiseInputY = offset * 0.02
                    val valueY = dimensFloat.height - noise2D(noiseInputX, noiseInputY) * dimensFloat.height
                    val colorRed = (noise2D(noiseInputX, noiseInputY) * 0xFF).toInt()
                    val colorGreen = (noise2D(noiseInputX, noiseInputY + 10) * 0xFF).toInt()
                    val colorBlue = (noise2D(noiseInputX, noiseInputY + 20) * 0xFF).toInt()

                    val pointVector2D = Vector2D(x.toFloat() / loop * dimensFloat.width, valueY.toFloat())
                    it.drawPoints(
                        listOf(Offset(
                            pointVector2D.x + ((mouseVector.x - dimensFloat.width/2)/mouseInsensitivity) * (offset - slices/2),
                            pointVector2D.y + ((mouseVector.y)/mouseInsensitivity) * (offset - slices/2))),
                        PointMode.Points,
                        Color(colorRed, colorGreen, colorBlue, 0xFF),
                        2f
                    )
                }
            }
        }
    }
}
