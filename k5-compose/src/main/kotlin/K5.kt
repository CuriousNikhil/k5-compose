import androidx.compose.desktop.Window
import androidx.compose.desktop.WindowEvents
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.window.v1.MenuBar
import java.awt.image.BufferedImage

fun k5(
    title: String = "Compose K5 Window",
    size: IntSize = IntSize(800, 600),
    location: IntOffset = IntOffset.Zero,
    centered: Boolean = true,
    icon: BufferedImage? = null,
    menuBar: MenuBar? = null,
    undecorated: Boolean = false,
    resizable: Boolean = true,
    events: WindowEvents = WindowEvents(),
    onDismissRequest: (() -> Unit)? = null,
    init: K5.() -> Unit
) {
    val composeK5 = K5(
        title = title,
        size = size,
        location = location,
        centered = centered,
        icon = icon,
        menuBar = menuBar,
        undecorated = undecorated,
        resizable = resizable,
        events = events,
        onDismissRequest = onDismissRequest
    )
    composeK5.init()
}

class K5(
    val title: String,
    val size: IntSize,
    val location: IntOffset,
    val centered: Boolean,
    val icon: BufferedImage?,
    val menuBar: MenuBar?,
    val undecorated: Boolean,
    val resizable: Boolean,
    val events: WindowEvents,
    val onDismissRequest: (() -> Unit)?
) {

    private var stopLoop = false

    fun noLoop() {
        this.stopLoop = true
    }

    fun show(modifier: Modifier, content: (dt: Float, drawScope: DrawScope) -> Unit) {
        render(modifier, content)
    }

    private fun render(modifier: Modifier, content: (dt: Float, drawScope: DrawScope) -> Unit) = Window(
        title = title,
        size = size,
        location = location,
        centered = centered,
        icon = icon,
        menuBar = menuBar,
        undecorated = undecorated,
        resizable = resizable,
        events = events,
        onDismissRequest = onDismissRequest
    ) {

        val dt = remember { mutableStateOf(0f) }
        var startTime = remember { mutableStateOf(0L) }
        val previousTime = remember { mutableStateOf(System.nanoTime()) }

        Canvas(modifier = modifier) {
            var stepFrame = dt.value
            content(stepFrame, this)
        }
        if (!stopLoop) {
            requestAnimationFrame(dt, previousTime)
        }
    }

    /**
     * Run
     */
    @Composable
    private fun requestAnimationFrame(dt: MutableState<Float>, previousTime: MutableState<Long>) {
        LaunchedEffect(Unit) {
            while (true) {
                withFrameNanos {
                    dt.value = ((it - previousTime.value) / 1E7).toFloat()
                    previousTime.value = it
                }
            }
        }
    }
}
