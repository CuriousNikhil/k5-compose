package examples.art

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import k5
import kotlin.random.Random

data class Square(
    val x: Float,
    val y: Float,
    val width: Float,
    val height: Float,
    var color: Color = Color.White
) {

    fun show(drawScope: DrawScope) {
        drawScope.drawRect(
            color = Color.Black,
            topLeft = Offset(x, y),
            size = Size(width, height),
            style = Stroke(width = 30f)
        )
        drawScope.drawRect(
            color = color,
            topLeft = Offset(x, y),
            size = Size(width, height),
            style = Fill
        )
    }
}

fun showPietMondrian() = k5 {

    noLoop()
    var stepDivider by mutableStateOf(8f)
    var white = 0xffF2F5F1
    var colors = listOf(Color.Blue, Color.Black, Color.Yellow, Color.Red)

    val squares = mutableListOf<Square>()

    fun flushAndAdd() {
        squares.clear()
        squares.add(Square(0f, 0f, dimensFloat.width, dimensFloat.height))
    }
    flushAndAdd()

    fun splitOnX(square: Square, splitAt: Float) {
        val squareA = Square(
            x = square.x,
            y = square.y,
            width = square.width - (square.width - splitAt + square.x),
            height = square.height
        )

        val squareB = Square(
            x = splitAt,
            y = square.y,
            width = square.width - splitAt + square.x,
            height = square.height
        )

        squares.add(squareA)
        squares.add(squareB)
    }

    fun splitOnY(square: Square, splitAt: Float) {
        val squareA = Square(
            x = square.x,
            y = square.y,
            width = square.width,
            height = square.height - (square.height - splitAt + square.y)
        )

        val squareB = Square(
            x = square.x,
            y = splitAt,
            width = square.width,
            height = square.height - splitAt + square.y
        )

        squares.add(squareA)
        squares.add(squareB)
    }

    fun splitSquaresWith(x: Float, y: Float) {
        var i = squares.size - 1
        while (i >= 0) {
            val square = squares[i]
            if (x > 0f && x > square.x && x < square.x + square.width) {
                if (Random.nextFloat() > 0.5f) {
                    squares.removeAt(i)
                    splitOnX(square, x)
                }
            }
            if (y > 0f && y > square.y && y < square.y + square.height) {
                if (Random.nextFloat() > 0.5f) {
                    squares.removeAt(i)
                    splitOnY(square, y)
                }
            }
            i--
        }
    }

    fun generateSquares() {
        var step = dimensInt.width / stepDivider
        for (i in 0 until dimensInt.width step step.toInt()) {
            splitSquaresWith(0f, i.toFloat())
            splitSquaresWith(i.toFloat(), 0f)
        }
    }

    generateSquares()
    showWithControls(modifier = Modifier.background(Color(white)), controls = {
        Text(
            text = "Piet Mondrian Artwork",
            fontFamily = FontFamily.Cursive,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center,
            fontSize = 30.sp,
            modifier = Modifier.padding(12.dp)
        )
        Text("Squares' factor - ${stepDivider.toInt()}")
        Slider(
            value = stepDivider,
            onValueChange =
            {
                flushAndAdd()
                stepDivider = it
                generateSquares()
            },
            valueRange = 8f..16f
        )
    }) { scope ->

        for (i in 0..stepDivider.toInt()) {
            squares[(0 until squares.size).random()].color = colors[(colors.indices).random()]
        }
        squares.forEach {
            it.show(scope)
        }
    }
}
