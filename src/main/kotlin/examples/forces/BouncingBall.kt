package examples.forces

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
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
import androidx.compose.ui.unit.dp
import k5
import math.Vector2D
import math.multiply
import math.random

@OptIn(ExperimentalFoundationApi::class)
fun bouncingBall() = k5 {

    var enableWaterDrag by mutableStateOf(false)
    var enableFriction by mutableStateOf(false)
    var gravity by mutableStateOf(0.2f)
    var ballMassFactor by mutableStateOf(1f)
    var coeffOfDrag by mutableStateOf(0.3f)

    val playgroundSize = dimensFloat

    val balls = List(5) {
        Ball(
            (2f..dimensFloat.width).random(),
            6f,
            m = (8f..50f).random(),
            ballMassFactor = ballMassFactor,
            coeffOfDrag = coeffOfDrag,
            playgroundSize
        )
    }

    fun showDrag(ball: Ball) {
        if (ball.position.y >= playgroundSize.height / 2) {
            ball.drag()
        }
    }

    fun showWater(scope: DrawScope) {
        scope.drawRect(
            color = Color.Blue,
            topLeft = Offset(0f, playgroundSize.height / 2),
            size = Size(playgroundSize.width, playgroundSize.height / 2)
        )
    }

    showWithControls(
        modifier = Modifier.combinedClickable(
            onClick = {
                /**
                 * On mouse click, apply wind force in right direction
                 */
                val wind = Vector2D(50f, 0f)
                balls.forEach { it.applyForce(wind) }
            }
        ),
        controls = {
            Row(modifier = Modifier.padding(4.dp)) {
                Checkbox(checked = enableWaterDrag, onCheckedChange = { enableWaterDrag = it })
                Text("Enable water drag")
            }
            Row(modifier = Modifier.padding(4.dp)) {
                Checkbox(checked = enableFriction, onCheckedChange = { enableFriction = it })
                Text("Enable friction force")
            }

            Text(modifier = Modifier.padding(top = 4.dp), text = "Gravity")
            Slider(value = gravity, onValueChange = { gravity = it })

            Text(text = "Coefficient of Drag")
            Slider(value = coeffOfDrag, onValueChange = { coeffOfDrag = it })

            Text(text = "Ball's mass")
            Slider(value = ballMassFactor, onValueChange = { ballMassFactor = it })
        }
    ) { scope ->
        // Apply gravity force every time on ball
        val gravityVector = Vector2D(0f, gravity)

        /*
         * This will create some sort of water and air medium. When ball will hit the water
         * it'll experience drag force
         */
        if (enableWaterDrag) {
            showWater(scope)
        }

        balls.forEach { ball ->
            val weight = gravityVector.multiply(ball.mass)
            ball.applyForce(weight)

            if (enableFriction) {
                ball.friction()
            }

            /*
             * This will create some sort of water and air medium. When ball will hit the water
             * it'll experience drag force
             */
            if (enableWaterDrag) {
                showDrag(ball)
            }

            ball.update()
            ball.edges()
            ball.render(scope)
        }
    }
}
