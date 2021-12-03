package examples.elye

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import k5
import math.noise2D

fun perlinnoise2d() {
    val loop = 3000
    k5 {
        noLoop()
        show {
            for (offset in 0 until loop) {
                val colorRed = (0..255).random()
                val colorGreen = (0..255).random()
                val colorBlue = (0..255).random()
                for (x in 0 until loop) {
                    val noiseInputX = x * 0.002
                    val noiseInputY = offset * 0.002
                    val valueY = noise2D(noiseInputX, noiseInputY) * dimensFloat.height * 2

                    it.drawPoints(
                        listOf(Offset(x.toFloat() / loop * dimensFloat.width, valueY.toFloat())),
                        PointMode.Points,
                        Color(colorRed, colorGreen, colorBlue, 0x88),
                        2f
                    )
                }
            }
        }
    }
}
