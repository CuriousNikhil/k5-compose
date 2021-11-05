package examples.particles

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import k5
import math.map
import math.random

// TODO: [WIP] Fix the translated view
data class Star(val dimens: Size) {
    val width = dimens.width
    val height = dimens.height
    var x = (-width..width).random()
    var y = (-height..height).random()
    var z = (0f..width).random()
    var pz = z

    fun update() {
        z -= 20
        if (z < 1) {
            z = dimens.width
            x = (-width..width).random()
            y = (-height..height).random()
            pz = z
        }
    }

    fun showStar(drawScope: DrawScope) {
        val sx = map(this.x / this.z, -1f, 1f, -dimens.width, dimens.width)
        val sy = map(this.y / this.z, -1f, 1f, -dimens.height, dimens.height)
        val r = map(this.z, 0f, dimens.width, 16f, 0f)
        drawScope.drawCircle(Color.White, r, Offset(sx, sy))

        var px = map(this.x / this.pz, -1f, 1f, -dimens.width, dimens.width)
        var py = map(this.y / this.pz, -1f, 1f, -dimens.height, dimens.height)
        val stroke = map(this.pz, 0f, dimens.width, 16f, 1f)
        pz = z
        drawScope.drawLine(Color.White, Offset(px, py), Offset(sx, sy), strokeWidth = stroke)
    }
}

fun showStarField() = k5 {
    val stars = mutableListOf<Star>()
    repeat(600) {
        stars.add(Star(this.size))
    }

    show {
        it.translate(dimensFloat.width / 2, dimensFloat.height / 2) {
            for (star in stars) {
                star.update()
                star.showStar(it)
            }
        }
    }
}
