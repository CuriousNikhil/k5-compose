package examples.simulations

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import k5
import math.Vector2D
import math.map
import math.plusAssign
import math.random
import kotlin.random.Random

// https://en.wikipedia.org/wiki/Cistercian_numerals
val SYMBOL_PATH = arrayOf(
    arrayOf(0, 0), // 0
    arrayOf(0, 0, 1, 0), // 1
    arrayOf(0, 1, 1, 1), // 2
    arrayOf(0, 0, 1, 1), // 3
    arrayOf(0, 1, 1, 0), // 4
    arrayOf(0, 0, 1, 0, 0, 1), // 5
    arrayOf(1, 0, 1, 1), // 6
    arrayOf(0, 0, 1, 0, 1, 1), // 7
    arrayOf(0, 1, 1, 1, 1, 0), // 8
    arrayOf(0, 0, 1, 0, 1, 1, 0, 1), // 9
)

fun digitPath(n: Int): Path {
    val p = SYMBOL_PATH[n % 10]
    val path = Path()
    path.moveTo(p[0].toFloat(), p[1].toFloat())
    for (i in 2 until p.size step 2) {
        path.lineTo(p[i].toFloat(), p[i + 1].toFloat())
    }
    return path
}

/**
 * This symbol is Cistercian Symbol for integers
 * https://en.wikipedia.org/wiki/Cistercian_numerals
 */
data class Symbol(
    val x: Float,
    val y: Float,
    val velocity: Vector2D,
    val maxLife: Float,
    val number: Int,
    val symbolSize: Float = (6f..12f).random(),
    val symbolColor: Color = Color.Green,
) {

    var lifetime = maxLife
    var alpha = 1f
    val position = Vector2D(x, y)
    fun update() {
        position += velocity
        lifetime -= 2f
        alpha = map(lifetime, 1f, maxLife, 0f, 1f)
    }

    fun isVanished() = lifetime < 0f

    fun drawSymbol(drawScope: DrawScope) {
        drawScope.translate(position.x, position.y) {
            drawLine(
                symbolColor,
                Offset(0f, 0f),
                Offset(0f, 3f * symbolSize),
                strokeWidth = 0.3f * symbolSize,
                alpha = alpha
            )

            translate(0f, 0f) {
                scale(1f * symbolSize, 1f * symbolSize, Offset(0f, 0f)) {
                    val path1 = digitPath(number % 10)
                    drawPath(path1, symbolColor, style = Stroke(0.3f), alpha = alpha)
                }

                scale(-1f * symbolSize, 1f * symbolSize, Offset(0f, 0f)) {
                    val path2 = digitPath(number / 10 % 10)
                    drawPath(path2, symbolColor, style = Stroke(0.3f), alpha = alpha)
                }
            }
            translate(0f, 3f * symbolSize) {
                scale(1f * symbolSize, -1f * symbolSize, Offset(0f, 0f)) {
                    val path3 = digitPath(number / 100 % 10)
                    drawPath(path3, symbolColor, style = Stroke(0.3f), alpha = alpha)
                }

                scale(-1f * symbolSize, -1f * symbolSize, Offset(0f, 0f)) {
                    val path4 = digitPath(number / 1000 % 10)
                    drawPath(path4, symbolColor, style = Stroke(0.3f), alpha = alpha)
                }
            }
        }
    }
}

/**
 * Symbol emitter emits those Cistercian symbols
 */
data class SymbolEmitter(val dimens: Size, val emitterX: Float) {

    val symbolList = mutableListOf<Symbol>()
    val maxLife = (200f..400f).random()
    val velocity = Vector2D(0f, (15f..20f).random())

    private fun addSymbol() {
        symbolList.add(
            Symbol(
                emitterX, 0f,
                velocity,
                maxLife,
                (10..100).random()
            )
        )
    }

    fun update() {
        for (i in symbolList.size - 1 downTo 0) {
            symbolList[i].update()
            symbolList.removeAll { it.isVanished() }
        }

        if (symbolList.size < 15) {
            if (Random.nextFloat() > 0.3f) {
                addSymbol()
            }
        }
    }

    fun draw(drawScope: DrawScope) {
        symbolList.forEach {
            it.drawSymbol(drawScope)
        }
    }
}

fun showMatrixGreenRain() = k5 {

    val symbolEmitterList = mutableListOf<SymbolEmitter>()
    var positionx = 0f
    repeat(25) {
        if (positionx < dimensFloat.width) {
            positionx += (20f..60f).random()
        }
        symbolEmitterList.add(SymbolEmitter(dimensFloat, positionx))
    }

    show { drawScope ->
        symbolEmitterList.forEach {
            it.update()
            it.draw(drawScope)
        }
    }
}
