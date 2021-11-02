package examples.simulations

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import k5
import kotlin.random.Random

fun theTenPrint() = k5 {

    var x = 0f
    var y = 0f
    val spacing = 40f

    noLoop()

    show {
        while (y <= dimensFloat.height) {
            if (Random.nextFloat() < 0.5f) {
                it.drawLine(Color.Cyan, Offset(x, y), Offset(x + spacing, y + spacing))
            } else {
                it.drawLine(Color.White, Offset(x, y + spacing), Offset(x + spacing, y))
            }

            x += spacing
            if (x > dimensInt.width) {
                x = 0f
                y += spacing
            }
        }
    }
}
