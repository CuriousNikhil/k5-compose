
import androidx.compose.animation.core.withInfiniteAnimationFrameMillis
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

/**
 * Builder construct for the K5-compose.
 * Can be called like, main() = k5 {...}
 * All the params passed are applied to a [Window] component
 *
 * @param size The size param for the size of window - [Size]
 * The size is taken in floats in order to perform canvas related operations easily on floats which includes the
 * dimension of window
 * @param title The title of the window.
 * The title is displayed in the windows's native border.
 * @param size The initial size of the window.
 * @param icon The icon for the window displayed on the system taskbar.
 * @param undecorated Removes the native window border if set to true. The default value is false.
 * @param resizable Makes the window resizable if is set to true and unresizable if is set to
 * false. The default value is true.
 * @param focusable Can window receive focus
 * @param alwaysOnTop Should window always be on top of another windows
 * @param onPreviewKeyEvent This callback is invoked when the user interacts with the hardware
 * keyboard. It gives ancestors of a focused component the chance to intercept a [KeyEvent].
 * Return true to stop propagation of this event. If you return false, the key event will be
 * sent to this [onPreviewKeyEvent]'s child. If none of the children consume the event,
 * it will be sent back up to the root using the onKeyEvent callback.
 * @param onKeyEvent This callback is invoked when the user interacts with the hardware
 * keyboard. While implementing this callback, return true to stop propagation of this event.
 * If you return false, the key event will be sent to this [onKeyEvent]'s parent.
 */
fun k5(
    size: Size = Size(1000f, 1000f),
    title: String = "K5 Compose Playground",
    icon: Painter? = null,
    undecorated: Boolean = false,
    resizable: Boolean = false,
    enabled: Boolean = true,
    focusable: Boolean = true,
    alwaysOnTop: Boolean = false,
    onPreviewKeyEvent: (KeyEvent) -> Boolean = { false },
    onKeyEvent: (KeyEvent) -> Boolean = { false },
    init: K5.() -> Unit
) {
    K5(
        size,
        title,
        icon,
        undecorated,
        resizable,
        enabled,
        focusable,
        alwaysOnTop,
        onPreviewKeyEvent,
        onKeyEvent
    ).init()
}

class K5(
    val size: Size,
    val title: String,
    val icon: Painter?,
    val undecorated: Boolean,
    val resizable: Boolean,
    val enabled: Boolean,
    val focusable: Boolean,
    val alwaysOnTop: Boolean,
    val onPreviewKeyEvent: (KeyEvent) -> Boolean,
    val onKeyEvent: (KeyEvent) -> Boolean,
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
     * Use this property to get the actual [k5] Playground size in Floats. Subtracting the 56f - which is the toolbar height of the window.
     * When the size of the window is set with `size` param in [k5] builder, it's applied to window and when
     * the canvas is rendered in the window with [Modifier.fillMaxSize] it takes whole window except the toolbar.
     *
     * TODO: Fix the dimensions for a given k5 playground considering density
     */
    val dimensFloat = Size(size.width, size.height - 56f)

    /**
     * Use this property to get the actual [k5] Playground size in Ints
     */
    val dimensInt = IntSize(size.width.toInt(), size.height.toInt() - 56)

    /**
     * Shows the canvas window and renders it for each frame repetitively.
     * Internally, this starts the Jetpack Compose Window and renders the [sketch] requested by user into the Jetpack Compose
     * [Canvas] Composable. The size of the [Canvas] will be same as the [size] passed in [k5] method by default.
     * One can change the canvas size and window size with the help of modifiers.
     * In order to keep the animation running (rendering canvas continuously), it requests to run the frame of animation in nanos.
     * All the [modifier] will be applied to the [Canvas].
     *
     * @param modifier Jetpack compose [Modifier]
     * @param sketch drawScope - Compose canvas drawscope
     */
    fun show(modifier: Modifier = Modifier.fillMaxSize(), sketch: (drawScope: DrawScope) -> Unit) {
        render(modifier, sketch)
    }

    /**
     * Starts the Jetpack Compose Window and renders the [sketch] requested by user into the Jetpack Compose
     * [Canvas] Composable.
     *
     * @param modifier - Jetpack compose [Modifier]. Will be applied to the [Canvas]
     * @param sketch - The content to be drawn on to [Canvas]
     */
    private fun render(modifier: Modifier, sketch: (drawScope: DrawScope) -> Unit) = application {
        val (width, height) = with(LocalDensity.current) { Pair(size.width.toDp(), size.height.toDp()) }
        Window(
            onCloseRequest = ::exitApplication,
            state = rememberWindowState(width = width, height = height),
            title = title,
            icon = icon,
            undecorated = undecorated,
            resizable = resizable,
            enabled = enabled,
            focusable = focusable,
            alwaysOnTop = alwaysOnTop,
            onPreviewKeyEvent = onPreviewKeyEvent,
            onKeyEvent = onKeyEvent
        ) {

            // dt = change in time
            val dt = remember { mutableStateOf(0f) }
            // TODO : Show elapsed time and frames per second on toolbar of window
            var startTime = remember { mutableStateOf(0L) }
            val previousTime = remember { mutableStateOf(System.nanoTime()) }
            Canvas(modifier = Modifier.fillMaxSize().background(Color.Black).then(modifier)) {
                val stepFrame = dt.value
                sketch(this)
            }
            if (!stopLoop) {
                requestAnimationFrame(dt, previousTime)
            }
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
                withInfiniteAnimationFrameMillis {
                    dt.value = ((it - previousTime.value) / 1E7).toFloat()
                    previousTime.value = it
                }
            }
        }
    }
}
