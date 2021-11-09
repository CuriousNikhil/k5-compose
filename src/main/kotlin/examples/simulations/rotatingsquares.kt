package examples.simulations

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import k5

data class Rect(
    val size: Dp,
    val offsetX: Dp,
    val offsetY: Dp,
    val color: Color
)

fun showRotatingSquares() = k5 {

    val n = 35
    val rectList = mutableListOf<Rect>()
    var angle = 0f

    val rectSize = 15.dp
    val colorList = listOf(
        Color(0xffffeaa7),
        Color(0xfffab1a0),
        Color(0xffa29bfe),
    )

    show {

        for (i in 0..n) {
            val s = rectSize * i
            val offsetX = -s / 2
            val offsetY = -s / 2
            val rect = Rect(s, offsetX, offsetY, colorList[i % colorList.size])
            rectList.add(rect)
        }

        it.translate(dimensFloat.width / 2, dimensFloat.height / 2) {
            for (i in n downTo 0) {
                this.rotate(angle * (n - i + 1) * 0.07f, Offset(0f, 0f)) {
                    drawRect(
                        brush = SolidColor(rectList[i].color),
                        Offset(rectList[i].offsetX.toPx(), rectList[i].offsetY.toPx()),
                        Size(rectList[i].size.toPx(), rectList[i].size.toPx()),
                        alpha = 0.7f
                    )
                }
            }
        }

        angle += 0.5f
    }
}
