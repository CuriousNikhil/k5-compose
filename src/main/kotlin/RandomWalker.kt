import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import kotlin.random.Random

fun randomWalk() = k5 {

    var x = 400f
    var y = 400f
    val points = mutableListOf<Offset>()
    points.add(Offset(x, y))
    show(modifier = Modifier.fillMaxSize().background(Color.Black)) { dt, drawScope ->
        val r = Random.nextInt(0, 5)
        when (r) {
            0 -> {
                x += 5
            }
            1 -> {
                x -= 5
            }
            2 -> {
                y += 5
            }
            4 -> {
                y -= 5
            }
        }

        points.add(Offset(x, y))
        drawScope.drawPoints(
            points, pointMode = PointMode.Lines, Color.Yellow,
            strokeWidth = 8f,
            cap = StrokeCap.Square
        )
    }
}
