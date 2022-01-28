package examples.simulations

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.input.pointer.pointerMoveFilter
import k5

/**
 * Recursive Fractal tree
 * Inspired from https://github.com/V9vek/Fractal-Trees
 */
// Implementation logic may differ
@OptIn(ExperimentalComposeUiApi::class)
fun showRecursiveTree() = k5 {

    noLoop()

    val degrees = mutableStateOf(45f)

    show(
        modifier = Modifier.pointerMoveFilter(onMove = {
            degrees.value = (it.x / dimensFloat.width) * 90f
            true
        })
    ) {
        it.apply {
            translate(this.size.width / 2, this.size.height) {
                // Draw a line
                drawLine(Color.White, start = Offset(0f, 0f), end = Offset(0f, -250f))
                // Move to the end of that line
                translate(0f, -250f) {
                    // Start the recursive branching
                    branch(250f, degrees.value)
                }
            }
        }
    }
}

fun DrawScope.branch(height: Float, degree: Float) {
    // Each branch will be 2/3rds the size of the previous one
    val h = height * 0.66f

    if (h > 2) {
        // Rotate by degree
        rotate(degree, Offset.Zero) {
            // Draw the branch
            drawLine(Color.White, start = Offset(0f, 0f), end = Offset(0f, -h))
            // Move to the end of the branch
            translate(0f, -h) {
                // call again to draw two new branches
                branch(h, degree)
            }
        }

        // Repeat the same thing, only branch off to the "left"
        rotate(-degree, Offset.Zero) {
            drawLine(Color.White, start = Offset(0f, 0f), end = Offset(0f, -h))
            translate(0f, -h) {
                branch(h, degree)
            }
        }
    }
}
