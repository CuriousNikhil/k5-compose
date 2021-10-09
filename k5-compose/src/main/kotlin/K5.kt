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

/**
 * Builder construct for the K5-compose.
 * Can be called like - main() = k5 {...}
 * All the params passed are applied to a [Window] component
 *
 * @param title The title of the window.
 * The title is displayed in the windows's native border.
 * @param size The initial size of the window.
 * @param location The initial position of the window in screen space. This parameter is
 * ignored if [center] is set to true.
 * @param centered Determines if the window is centered on startup. The default value for the
 * window is true.
 * @param icon The icon for the window displayed on the system taskbar.
 * @param menuBar Window menu bar. The menu bar can be displayed inside a window (Windows,
 * Linux) or at the top of the screen (Mac OS).
 * @param undecorated Removes the native window border if set to true. The default value is false.
 * @param resizable Makes the window resizable if is set to true and unresizable if is set to
 * false. The default value is true.
 * @param events Allows to describe events of the window.
 * Supported events: onOpen, onClose, onMinimize, onMaximize, onRestore, onFocusGet, onFocusLost,
 * onResize, onRelocate.
 * @param onDismissRequest Executes when the user tries to close the Window.
 */
fun k5(
    title: String = "Compose K5 Window",
    size: IntSize = IntSize(400, 400),
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

    /**
     * Call method to stop the looping of canvas
     * You can also call it to freeze the time frame for a canvas
     */
    fun noLoop() {
        this.stopLoop = true
    }

    /**
     * Shows the canvas window and renders it for each frame repetitively
     *
     * @param modifier Jetpack compose [Modifier]
     * @param content dt - change in time
     *                drawScope - Compose canvas drawscope
     */
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
     * Run frame time with nanoseconds
     * @param dt - Change it time
     * @param previousTime - previous time to calculate change in time
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
