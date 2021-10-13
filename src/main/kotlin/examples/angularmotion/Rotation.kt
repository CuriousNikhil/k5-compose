package examples.angularmotion

import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotateRad
import androidx.compose.ui.input.pointer.pointerMoveFilter
import k5
import math.Vector2D
import math.constrain
import math.map
import math.toOffSet

fun rotateRectangle() = k5 {

    var angle = 0f
    var angularVelocity = 0f
    var angularAcc = 0.001f

    val position = Vector2D(getPlaygroundDimensions().width / 2, getPlaygroundDimensions().height / 2).toOffSet()

    var mouseX = 0f
    show(
        modifier = Modifier.pointerMoveFilter(onMove = {
            mouseX = it.x
            false
        })
    ) { dt, drawScope ->

        // Map the angular acceleration between -0.01f to 0.01f.
        // Map function basically re-maps a number from one range to another.
        angularAcc = map(mouseX, 0f, getPlaygroundDimensions().width, -0.01f, 0.01f)

        // add constrain on the angular velocity value between given low and high
        angularVelocity = constrain(angularVelocity, -0.2f, 0.2f)

        drawScope.rotateRad(angle, pivot = drawScope.size.center) {
            drawScope.drawRect(Color.Cyan, topLeft = Offset(position.x - 30, position.y - 90), size = Size(60f, 180f))
        }

        // THe concept of angular velocity and angular acceleration works the same way!
        // add angular velocity to angular acceleration
        angularVelocity += angularAcc
        // Add angular velocity to angle
        angle += angularVelocity
    }
}
