package examples.art

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import k5
import math.Vector2D
import kotlin.random.Random

fun showTriangularMesh() = k5 {

    lateinit var line: MutableList<Vector2D>
    val grid = mutableListOf<MutableList<Vector2D>>()
    var odd = false

    val gap = dimensInt.width / 9

    noLoop()
    val gray = listOf(0xff808080, 0xff888888, 0xff909090, 0xff989898, 0xffA0A0A0, 0xffA8A8A8)
    val colors = listOf(Color.Gray, Color.Red, Color.Blue, Color.Green, Color.Yellow, Color.Cyan, Color.Magenta, Color.White)
    val lightColors = listOf(0xff55efc4, 0xff81ecec, 0xffa29bfe, 0xffdfe6e9, 0xffffeaa7, 0xffff7675, 0xffe17055, 0xff0984e3, 0xffe84393)
    val ltColors = listOf(0xfff1c40f, 0xffe67e22, 0xffe74c3c, 0xff3498db, 0xff9b59b6, 0xff34495e, 0xff2ecc71)

    fun DrawScope.drawTriangle(p1: Vector2D, p2: Vector2D, p3: Vector2D) {
        val path = Path()
        path.moveTo(p1.x, p1.y)
        path.lineTo(p2.x, p2.y)
        path.lineTo(p3.x, p3.y)
        path.lineTo(p1.x, p1.y)
        path.close()
        this.drawPath(path, Color(ltColors[(ltColors.indices).random()]))
    }

    show {

        for (y in (gap / 2)..dimensInt.width step gap) {
            odd = !odd
            line = mutableListOf()
            for (x in (gap / 2)..dimensInt.width step gap) {
                val vector = Vector2D(x.toFloat() + if (odd) gap / 2 else 0, y.toFloat())
                line.add(
                    Vector2D(
                        x = x + (Random.nextFloat() * 0.8f - 0.4f) * gap + (if (odd) gap / 2 else 0),
                        y = y + (Random.nextFloat() * 0.8f - 0.4f) * gap
                    )
                    // vector
                )
            }
            grid.add(line)
        }

        lateinit var dotLine: MutableList<Vector2D>
        odd = true
        for (y in 0 until grid.size - 1) {
            odd = !odd
            dotLine = mutableListOf()
            for (i in 0 until grid[y].size) {
                dotLine.add(if (odd) grid[y][i] else grid[y + 1][i])
                dotLine.add(if (odd) grid[y + 1][i] else grid[y][i])
            }
            for (j in 0 until dotLine.size - 2) {
                it.drawTriangle(dotLine[j], dotLine[j + 1], dotLine[j + 2])
            }
        }
    }
}
