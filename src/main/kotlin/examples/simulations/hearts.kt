package examples.simulations

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.translate
import k5

fun showHearts() = k5 {

    var factor = 0f
    val scale = 2.0f
    var index = 0
    val n = 18

    val colors = listOf(
        Color(0xFFE03776),
        Color(0xFF8F3E98),
        Color(0xFF4687BF),
        Color(0xFF3BAB6F),
        Color(0xFFF9C25E),
        Color(0xFFF47274),
    )

    show {

        factor += 0.04f
        if (factor > scale) {
            factor -= scale
            index += 1
        }

        it.apply {
            translate(dimensFloat.width / 2, dimensFloat.height / 2) {
                for (i in n downTo 0) {
                    val ithscale = factor + i * scale
                    val path = Path()
                    path.moveTo(0.0f * ithscale, 12.0f * ithscale)
                    path.cubicTo(
                        50.0f * ithscale,
                        -30.0f * ithscale,
                        110.0f * ithscale,
                        50.0f * ithscale,
                        0.0f * ithscale,
                        120.0f * ithscale
                    )
                    path.cubicTo(
                        -110.0f * ithscale,
                        50.0f * ithscale,
                        -50.0f * ithscale,
                        -30.0f * ithscale,
                        0.0f * ithscale,
                        12.0f * ithscale
                    )
                    path.close()
                    path.translate(Offset(0.0f, -69.0f * ithscale))

                    drawPath(path, colors[(index + (colors.size - i % colors.size)) % colors.size])
                }
            }
        }
    }
}
