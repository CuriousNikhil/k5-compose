package examples.elye

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import k5
import math.noise1D

fun perlinnoise1d() {
    k5 {
        noLoop()
        show {
            for (offset in 0 until 10) {
                val colorRed = (0..255).random()
                val colorGreen = (0..255).random()
                val colorBlue = (0..255).random()
                for (x in 0 until 3000) {
                    val noiseInputX = x * 0.002 + offset * 10
                    val valueY = noise1D((noiseInputX)) * dimensFloat.height * 2

                    it.drawPoints(
                        listOf(Offset(x.toFloat() / 3000 * dimensFloat.width, valueY.toFloat())),
                        PointMode.Points,
                        Color(colorRed, colorGreen, colorBlue, 0x88),
                        2f
                    )
                }
            }
        }
    }
}

