import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MoodPlot(
    coordinates: Map<Int, Int?>,
    modifier:Modifier = Modifier,
    xMaxValue: Int = 12,
    xMinValue: Int = 1,
    yMaxValue: Int = 100,
    yMinValue: Int = 0,
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val padding = 48.dp.toPx()
        val canvasWidth = size.width
        val canvasHeight = size.height
        val graphWidth = canvasWidth - 2 * padding
        val graphHeight = canvasHeight - 2 * padding

        // Draw axes (same as before)
        drawLine(
            color = Color.Black,
            start = Offset(padding, padding + graphHeight),
            end = Offset(padding + graphWidth, padding + graphHeight),
            strokeWidth = 3.dp.toPx()
        )
        drawLine(
            color = Color.Black,
            start = Offset(padding, padding + graphHeight),
            end = Offset(padding, padding),
            strokeWidth = 3.dp.toPx()
        )

        // X-axis labels (1-12)
        val xStep = graphWidth / (xMaxValue - xMinValue + 1)
        for (i in xMinValue..xMaxValue) {
            val x = padding + xStep * (i - xMinValue)
            drawLine(
                color = Color.Black,
                start = Offset(x, padding + graphHeight),
                end = Offset(x, padding + graphHeight + 5.dp.toPx()),
                strokeWidth = 1.dp.toPx()
            )

            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    i.toString(),
                    x,
                    padding + graphHeight + 20.dp.toPx(),
                    android.graphics.Paint().apply {
                        color = android.graphics.Color.BLACK
                        textSize = 12.sp.toPx()
                        textAlign = android.graphics.Paint.Align.CENTER
                    }
                )
            }
        }

        // Y-axis labels (33, 66, 99 with text)
        val yPositions = listOf(33, 66, 99)
        val yLabels = listOf("Bad", "Normal", "Good")

        for ((index, yValue) in yPositions.withIndex()) {
            val y = padding + graphHeight - (graphHeight * yValue / 100f)

            drawLine(
                color = Color.Black,
                start = Offset(padding - 5.dp.toPx(), y),
                end = Offset(padding, y),
                strokeWidth = 1.dp.toPx()
            )

            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    yLabels[index],
                    padding - 10.dp.toPx(),
                    y + 4.dp.toPx(),
                    android.graphics.Paint().apply {
                        color = android.graphics.Color.BLACK
                        textSize = 12.sp.toPx()
                        textAlign = android.graphics.Paint.Align.RIGHT
                    }
                )
            }
        }

        // Draw plot with connecting lines
        val sortedEntries = coordinates.entries.sortedBy { it.key }
        var previousValidPoint: Offset? = null

        sortedEntries.forEach { (xValue, yValue) ->
            if (yValue == null) {
                previousValidPoint = null
                return@forEach
            }

            val x = padding + (xValue - xMinValue) * xStep
            val y = padding + graphHeight - (graphHeight * yValue / 100f)
            val currentPoint = Offset(x, y)

            previousValidPoint?.let { prevPoint ->
                drawLine(
                    color = Color(red = 139, green = 69, blue = 19),
                    start = prevPoint,
                    end = currentPoint,
                    strokeWidth = 2.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }

            previousValidPoint = currentPoint
        }

        sortedEntries.forEach { (xValue, yValue) ->
            if (yValue == null) return@forEach

            val x = padding + (xValue - xMinValue) * xStep
            val y = padding + graphHeight - (graphHeight * yValue / 100f)

            drawCircle(
                color = Color.Black,
                radius = 5.dp.toPx(),
                center = Offset(x, y)
            )
        }
    }
}