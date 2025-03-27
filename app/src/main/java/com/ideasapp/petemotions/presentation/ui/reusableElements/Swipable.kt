package com.ideasapp.petemotions.presentation.ui.reusableElements

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.coroutineScope
import kotlin.math.abs
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.awaitHorizontalTouchSlopOrCancellation
import androidx.compose.foundation.gestures.drag
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.AwaitPointerEventScope
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNode
import androidx.compose.ui.input.pointer.changedToUpIgnoreConsumed
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.input.pointer.util.addPointerInputChange
import androidx.compose.ui.node.DelegatingNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.PointerInputModifierNode
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.Velocity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.isActive
import kotlin.coroutines.cancellation.CancellationException
import kotlin.math.sign


@Composable
fun SwipeableActionsBox(
    modifier: Modifier = Modifier,
    state: SwipeableActionsState = rememberSwipeableActionsState(),
    startActions: List<SwipeAction> = emptyList(),
    endActions: List<SwipeAction> = emptyList(),
    swipeThreshold: Dp = 40.dp,
    backgroundUntilSwipeThreshold: Color = Color.DarkGray,
    content: @Composable BoxScope.() -> Unit
) = Box(modifier) {
    state.also {
        it.swipeThresholdPx = LocalDensity.current.run { swipeThreshold.toPx() }
        val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl
        it.actions = remember(endActions, startActions, isRtl) {
            ActionFinder(
                left = if (isRtl) endActions else startActions,
                right = if (isRtl) startActions else endActions,
            )
        }
    }

    val backgroundColor = when {
        state.swipedAction != null -> state.swipedAction!!.value.background
        !state.hasCrossedSwipeThreshold() -> backgroundUntilSwipeThreshold
        state.visibleAction != null -> state.visibleAction!!.value.background
        else -> Color.Transparent
    }
    val animatedBackgroundColor: Color = if (state.layoutWidth == 0) {
        // Use the current color immediately because paparazzi can only capture the 1st frame.
        // https://github.com/cashapp/paparazzi/issues/1261
        backgroundColor
    } else {
        animateColorAsState(backgroundColor).value
    }

    val scope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .onSizeChanged { state.layoutWidth = it.width }
            .absoluteOffset { IntOffset(x = state.offset.value.roundToInt(), y = 0) }
            .drawOverContent { state.ripple.draw(scope = this) }
            .horizontalDraggable(
                enabled = !state.isResettingOnRelease,
                onDragStopped = {
                    scope.launch {
                        state.handleOnDragStopped()
                    }
                },
                state = state.draggableState,
            ),
        content = content
    )

    (state.swipedAction ?: state.visibleAction)?.let { action ->
        ActionIconBox(
            modifier = Modifier.matchParentSize(),
            action = action,
            offset = state.offset.value,
            backgroundColor = animatedBackgroundColor,
            content = { action.value.icon() }
        )
    }

    val hapticFeedback = LocalHapticFeedback.current
    if (state.hasCrossedSwipeThreshold() && state.swipedAction == null) {
        LaunchedEffect(state.visibleAction) {
            hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
        }
    }
}

@Composable
private fun ActionIconBox(
    action: SwipeActionMeta,
    offset: Float,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Row(
        modifier = modifier
            .layout { measurable, constraints ->
                val placeable = measurable.measure(constraints)
                layout(width = placeable.width, height = placeable.height) {
                    // Align icon with the left/right edge of the content being swiped.
                    val iconOffset = if (action.isOnRightSide) constraints.maxWidth + offset else offset - placeable.width
                    placeable.placeRelative(x = iconOffset.roundToInt(), y = 0)
                }
            }
            .background(color = backgroundColor),
        horizontalArrangement = if (action.isOnRightSide) Arrangement.Start else Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        content()
    }
}

private fun Modifier.drawOverContent(onDraw: DrawScope.() -> Unit): Modifier {
    return drawWithContent {
        drawContent()
        onDraw(this)
    }
}
/**
 * Represents an action that can be shown in [SwipeableActionsBox].
 *
 * @param background Color used as the background of [SwipeableActionsBox] while
 * this action is visible. If this action is swiped, its background color is
 * also used for drawing a ripple over the content for providing a visual
 * feedback to the user.
 *
 * @param weight The proportional width to give to this element, as related
 * to the total of all weighted siblings. [SwipeableActionsBox] will divide its
 * horizontal space and distribute it to actions according to their weight.
 *
 * @param isUndo Determines the direction in which a ripple is drawn when this
 * action is swiped. When false, the ripple grows from this action's position
 * to consume the entire composable, and vice versa. This can be used for
 * actions that can be toggled on and off.
 */
class SwipeAction(
    val onSwipe: () -> Unit,
    val icon: @Composable () -> Unit,
    val background: Color,
    val weight: Double = 1.0,
    val isUndo: Boolean = false
) {
    init {
        require(weight > 0.0) { "invalid weight $weight; must be greater than zero" }
    }

    fun copy(
        onSwipe: () -> Unit = this.onSwipe,
        icon: @Composable () -> Unit = this.icon,
        background: Color = this.background,
        weight: Double = this.weight,
        isUndo: Boolean = this.isUndo,
    ) = SwipeAction(
        onSwipe = onSwipe,
        icon = icon,
        background = background,
        weight = weight,
        isUndo = isUndo
    )
}

/**
 * See [SwipeAction] for documentation.
 */
fun SwipeAction(
    onSwipe: () -> Unit,
    icon: Painter,
    background: Color,
    weight: Double = 1.0,
    isUndo: Boolean = false
): SwipeAction {
    return SwipeAction(
        icon = {
            Image(
                modifier = Modifier.padding(16.dp),
                painter = icon,
                contentDescription = null
            )
        },
        background = background,
        weight = weight,
        onSwipe = onSwipe,
        isUndo = isUndo
    )
}


@Composable
fun rememberSwipeableActionsState(): SwipeableActionsState {
    return remember { SwipeableActionsState() }
}

/**
 * The state of a [SwipeableActionsBox].
 */
@Stable
class SwipeableActionsState internal constructor() {
    /**
     * The current position (in pixels) of a [SwipeableActionsBox].
     */
    val offset: State<Float> get() = offsetState
    internal var offsetState = mutableStateOf(0f)

    /**
     * Whether [SwipeableActionsBox] is currently animating to reset its offset after it was swiped.
     */
    val isResettingOnRelease: Boolean by derivedStateOf {
        swipedAction != null
    }

    internal var layoutWidth: Int by mutableIntStateOf(0)
    internal var swipeThresholdPx: Float by mutableFloatStateOf(0f)
    internal val ripple = SwipeRippleState()

    internal var actions: ActionFinder by mutableStateOf(
        ActionFinder(left = emptyList(), right = emptyList())
    )
    internal val visibleAction: SwipeActionMeta? by derivedStateOf {
        actions.actionAt(offsetState.value, totalWidth = layoutWidth)
    }
    internal var swipedAction: SwipeActionMeta? by mutableStateOf(null)

    internal val draggableState = DraggableState { delta ->
        val targetOffset = offsetState.value + delta

        val canSwipeTowardsRight = actions.left.isNotEmpty()
        val canSwipeTowardsLeft = actions.right.isNotEmpty()

        val isAllowed = isResettingOnRelease
                || targetOffset == 0f
                || (targetOffset > 0f && canSwipeTowardsRight)
                || (targetOffset < 0f && canSwipeTowardsLeft)
        offsetState.value += if (isAllowed) delta else delta / 10
    }

    internal fun hasCrossedSwipeThreshold(): Boolean {
        return abs(offsetState.value) > swipeThresholdPx
    }

    internal suspend fun handleOnDragStopped() = coroutineScope {
        launch {
            if (hasCrossedSwipeThreshold()) {
                visibleAction?.let { action ->
                    swipedAction = action
                    action.value.onSwipe()
                    ripple.animate(action = action)
                }
            }
        }
        launch {
            draggableState.drag(MutatePriority.PreventUserInput) {
                Animatable(offsetState.value).animateTo(
                    targetValue = 0f,
                    animationSpec = tween(durationMillis = animationDurationMs),
                ) {
                    dragBy(value - offsetState.value)
                }
            }
            swipedAction = null
        }
    }
}

@Stable
internal class SwipeRippleState {
    private var ripple = mutableStateOf<SwipeRipple?>(null)

    suspend fun animate(
        action: SwipeActionMeta,
    ) {
        val drawOnRightSide = action.isOnRightSide
        val action = action.value

        ripple.value = SwipeRipple(
            isUndo = action.isUndo,
            rightSide = drawOnRightSide,
            color = action.background,
            alpha = 0f,
            progress = 0f
        )

        // Reverse animation feels faster (especially for larger swipe distances) so slow it down further.
        val animationDurationMs = (animationDurationMs * (if (action.isUndo) 1.75f else 1f)).roundToInt()

        coroutineScope {
            launch {
                Animatable(initialValue = 0f).animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = animationDurationMs),
                    block = {
                        ripple.value = ripple.value!!.copy(progress = value)
                    }
                )
            }
            launch {
                Animatable(initialValue = if (action.isUndo) 0f else 0.25f).animateTo(
                    targetValue = if (action.isUndo) 0.5f else 0f,
                    animationSpec = tween(
                        durationMillis = animationDurationMs,
                        delayMillis = if (action.isUndo) 0 else animationDurationMs / 2
                    ),
                    block = {
                        ripple.value = ripple.value!!.copy(alpha = value)
                    }
                )
            }
        }
    }

    fun draw(scope: DrawScope) {
        ripple.value?.run {
            scope.clipRect {
                val size = scope.size
                // Start the ripple with a radius equal to the available height so that it covers the entire edge.
                val startRadius = if (isUndo) size.width + size.height else size.height
                val endRadius = if (!isUndo) size.width + size.height else size.height
                val radius = lerp(startRadius, endRadius, fraction = progress)

                drawCircle(
                    color = color,
                    radius = radius,
                    alpha = alpha,
                    center = this.center.copy(x = if (rightSide) this.size.width + this.size.height else 0f - this.size.height)
                )
            }
        }
    }
}

private data class SwipeRipple(
    val isUndo: Boolean,
    val rightSide: Boolean,
    val color: Color,
    val alpha: Float,
    val progress: Float,
)

private fun lerp(start: Float, stop: Float, fraction: Float) =
    (start * (1 - fraction) + stop * fraction)


internal data class SwipeActionMeta(
    val value: SwipeAction,
    val isOnRightSide: Boolean,
)

internal data class ActionFinder(
    val left: List<SwipeAction>,
    val right: List<SwipeAction>
) {

    fun actionAt(offset: Float, totalWidth: Int): SwipeActionMeta? {
        if (offset == 0f) {
            return null
        }

        val isOnRightSide = offset < 0f
        val actions = if (isOnRightSide) right else left

        val actionAtOffset = actions.actionAt(
            offset = abs(offset).coerceAtMost(totalWidth.toFloat()),
            totalWidth = totalWidth
        )
        return actionAtOffset?.let {
            SwipeActionMeta(
                value = actionAtOffset,
                isOnRightSide = isOnRightSide
            )
        }
    }

    private fun List<SwipeAction>.actionAt(offset: Float, totalWidth: Int): SwipeAction? {
        if (isEmpty()) {
            return null
        }

        val totalWeights = this.sumOf { it.weight }
        var offsetSoFar = 0.0

        @Suppress("ReplaceManualRangeWithIndicesCalls") // Avoid allocating an Iterator for every pixel swiped.
        for (i in 0 until size) {
            val action = this[i]
            val actionWidth = (action.weight / totalWeights) * totalWidth
            val actionEndX = offsetSoFar + actionWidth

            if (offset <= actionEndX) {
                return action
            }
            offsetSoFar += actionEndX
        }

        // Precision error in the above loop maybe?
        error("Couldn't find any swipe action. Width=$totalWidth, offset=$offset, actions=$this")
    }
}

internal const val animationDurationMs = 4_00

internal fun Modifier.horizontalDraggable(
    state: DraggableState,
    enabled: Boolean = true,
    startDragImmediately: Boolean = false,
    onDragStarted: suspend CoroutineScope.(startedPosition: Offset) -> Unit = {},
    onDragStopped: suspend CoroutineScope.(velocity: Float) -> Unit = {},
): Modifier = this then DraggableElement(
    state = state,
    enabled = enabled,
    startDragImmediately = { startDragImmediately },
    onDragStarted = onDragStarted,
    onDragStopped = { velocity -> onDragStopped(velocity.x) },
)

internal data class DraggableElement(
    private val state: DraggableState,
    private val enabled: Boolean,
    private val startDragImmediately: () -> Boolean,
    private val onDragStarted: suspend CoroutineScope.(startedPosition: Offset) -> Unit,
    private val onDragStopped: suspend CoroutineScope.(velocity: Velocity) -> Unit,
) : ModifierNodeElement<DraggableNode>() {

    override fun create(): DraggableNode = DraggableNode(
        state,
        enabled,
        startDragImmediately,
        onDragStarted,
        onDragStopped,
    )

    override fun update(node: DraggableNode) {
        node.update(
            state = state,
            enabled = enabled,
            startDragImmediately = startDragImmediately,
            onDragStarted = onDragStarted,
            onDragStopped = onDragStopped,
        )
    }

    override fun InspectorInfo.inspectableProperties() {
        name = "draggable"
        properties["enabled"] = enabled
        properties["startDragImmediately"] = startDragImmediately
        properties["onDragStarted"] = onDragStarted
        properties["onDragStopped"] = onDragStopped
        properties["state"] = state
    }
}

internal class DraggableNode(
    private var state: DraggableState,
    private var enabled: Boolean,
    private var startDragImmediately: () -> Boolean,
    private var onDragStarted: suspend CoroutineScope.(startedPosition: Offset) -> Unit,
    private var onDragStopped: suspend CoroutineScope.(velocity: Velocity) -> Unit,
) : DelegatingNode(), PointerInputModifierNode {

    private val velocityTracker = VelocityTracker()
    private val channel = Channel<DragEvent>(capacity = Channel.UNLIMITED)

    @Suppress("NAME_SHADOWING")
    private val pointerInputNode = SuspendingPointerInputModifierNode {
        if (!enabled) {
            return@SuspendingPointerInputModifierNode
        }

        coroutineScope {
            launch(start = CoroutineStart.UNDISPATCHED) {
                while (isActive) {
                    var event = channel.receive()
                    if (event !is DragEvent.DragStarted) continue
                    onDragStarted.invoke(this, event.startPoint)
                    try {
                        state.drag(MutatePriority.UserInput) {
                            while (event !is DragEvent.DragStopped && event !is DragEvent.DragCancelled) {
                                (event as? DragEvent.DragDelta)?.let { dragBy(it.delta.x) }
                                event = channel.receive()
                            }
                        }
                        event.let { event ->
                            if (event is DragEvent.DragStopped) {
                                onDragStopped.invoke(this, event.velocity)
                            } else if (event is DragEvent.DragCancelled) {
                                onDragStopped.invoke(this, Velocity.Zero)
                            }
                        }
                    } catch (c: CancellationException) {
                        onDragStopped.invoke(this, Velocity.Zero)
                    }
                }
            }

            awaitEachGesture {
                val awaited = awaitDownAndSlop(
                    startDragImmediately = startDragImmediately,
                    velocityTracker = velocityTracker,
                )

                if (awaited != null) {
                    var isDragSuccessful = false
                    try {
                        isDragSuccessful = awaitDrag(
                            startEvent = awaited.first,
                            initialDelta = awaited.second,
                            velocityTracker = velocityTracker,
                            channel = channel,
                            reverseDirection = false,
                        )
                    } catch (cancellation: CancellationException) {
                        isDragSuccessful = false
                        if (!isActive) throw cancellation
                    } finally {
                        val event = if (isDragSuccessful) {
                            val velocity = velocityTracker.calculateVelocity()
                            velocityTracker.resetTracking()
                            DragEvent.DragStopped(velocity)
                        } else {
                            DragEvent.DragCancelled
                        }
                        channel.trySend(event)
                    }
                }
            }
        }
    }

    init {
        delegate(pointerInputNode)
    }

    override fun onPointerEvent(
        pointerEvent: PointerEvent,
        pass: PointerEventPass,
        bounds: IntSize
    ) {
        pointerInputNode.onPointerEvent(pointerEvent, pass, bounds)
    }

    override fun onCancelPointerInput() {
        pointerInputNode.onCancelPointerInput()
    }

    fun update(
        state: DraggableState,
        enabled: Boolean,
        startDragImmediately: () -> Boolean,
        onDragStarted: suspend CoroutineScope.(startedPosition: Offset) -> Unit,
        onDragStopped: suspend CoroutineScope.(velocity: Velocity) -> Unit,
    ) {
        var resetPointerInputHandling = false
        if (this.state != state) {
            this.state = state
            resetPointerInputHandling = true
        }
        if (this.enabled != enabled) {
            this.enabled = enabled
            resetPointerInputHandling = true
        }
        this.startDragImmediately = startDragImmediately
        this.onDragStarted = onDragStarted
        this.onDragStopped = onDragStopped
        if (resetPointerInputHandling) {
            pointerInputNode.resetPointerInputHandler()
        }
    }
}

private suspend fun AwaitPointerEventScope.awaitDownAndSlop(
    startDragImmediately: () -> Boolean,
    velocityTracker: VelocityTracker,
): Pair<PointerInputChange, Offset>? {
    val initialDown = awaitFirstDown(requireUnconsumed = false, pass = PointerEventPass.Initial)
    return if (startDragImmediately()) {
        initialDown.consume()
        velocityTracker.addPointerInputChange(initialDown)
        // since we start immediately we don't wait for slop and the initial delta is 0
        initialDown to Offset.Zero
    } else {
        val down = awaitFirstDown(requireUnconsumed = false)
        velocityTracker.addPointerInputChange(down)
        var initialDelta = Offset.Zero
        val postPointerSlop = { event: PointerInputChange, overSlop: Float ->
            val isHorizontalSwipe = event.positionChange().let {
                abs(it.x) > abs(it.y * 2f)  // Accept swipes made at a max. of 22.5° in either direction.
            }
            if (isHorizontalSwipe) {
                velocityTracker.addPointerInputChange(event)
                event.consume()
                initialDelta = Offset(x = overSlop, 0f)
            } else {
                throw CancellationException()
            }
        }

        val afterSlopResult = awaitHorizontalTouchSlopOrCancellation(
            pointerId = down.id,
            onTouchSlopReached = postPointerSlop
        )

        if (afterSlopResult != null) afterSlopResult to initialDelta else null
    }
}

private suspend fun AwaitPointerEventScope.awaitDrag(
    startEvent: PointerInputChange,
    initialDelta: Offset,
    velocityTracker: VelocityTracker,
    channel: SendChannel<DragEvent>,
    reverseDirection: Boolean,
): Boolean {
    val overSlopOffset = initialDelta
    val xSign = sign(startEvent.position.x)
    val ySign = sign(startEvent.position.y)
    val adjustedStart = startEvent.position -
            Offset(overSlopOffset.x * xSign, overSlopOffset.y * ySign)
    channel.trySend(DragEvent.DragStarted(adjustedStart))

    channel.trySend(DragEvent.DragDelta(if (reverseDirection) initialDelta * -1f else initialDelta))

    return drag(pointerId = startEvent.id) { event ->
        // Velocity tracker takes all events, even UP
        velocityTracker.addPointerInputChange(event)

        // Dispatch only MOVE events
        if (!event.changedToUpIgnoreConsumed()) {
            val delta = event.positionChange()
            event.consume()
            channel.trySend(DragEvent.DragDelta(if (reverseDirection) delta * -1f else delta))
        }
    }
}

private sealed class DragEvent {
    class DragStarted(val startPoint: Offset) : DragEvent()
    class DragStopped(val velocity: Velocity) : DragEvent()
    data object DragCancelled : DragEvent()
    class DragDelta(val delta: Offset) : DragEvent()
}