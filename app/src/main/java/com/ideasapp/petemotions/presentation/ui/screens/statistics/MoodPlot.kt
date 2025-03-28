package com.ideasapp.petemotions.presentation.ui.screens.statistics

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.sp

@Composable
fun MoodPlot(
    coordinates:Map<Int,Int>,
    modifier:Modifier = Modifier,
    xMaxValue:Int = 10,
    xMinValue:Int = 1,
    yMaxValue:Int = 10,
    yMinValue:Int = 1,
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        // Dimensions of the drawing area
        val width = size.width
        val height = size.height

        // Padding around the plot
        val padding = 50f

        // Calculate the drawable area
        val drawableWidth = width - padding * 2
        val drawableHeight = height - padding * 2

        // Calculate x and y step sizes
        val xStep = drawableWidth / (xMaxValue - xMinValue)
        val yStep = drawableHeight / (yMaxValue - yMinValue)

        // Draw axes
        drawLine(
            color = Color.Black,
            start = Offset(padding, height - padding),
            end = Offset(width - padding, height - padding),
            strokeWidth = 4f
        )
        drawLine(
            color = Color.Black,
            start = Offset(padding, height - padding),
            end = Offset(padding, padding),
            strokeWidth = 4f
        )

        // Draw labels and grid lines
        val textPaint = android.graphics.Paint().apply {
            color = android.graphics.Color.BLACK
            textSize = 24.sp.toPx()
            isAntiAlias = true
        }
        for (i in xMinValue..xMaxValue) {
            // X-axis labels
            val x = padding + (i - xMinValue) * xStep
            drawContext.canvas.nativeCanvas.drawText(
                i.toString(),
                x,
                height - padding + 30f,
                textPaint
            )
        }
        for (i in yMinValue..yMaxValue step 10) {
            // Y-axis labels
            val y = height - padding - (i - yMinValue) * yStep
            drawContext.canvas.nativeCanvas.drawText(
                i.toString(),
                padding - 40f,
                y + 10f,
                textPaint
            )
        }

        // Draw the mood line
        val path = Path()
        coordinates.entries.sortedBy { it.key }.forEachIndexed { index, entry ->
            val x = padding + (entry.key - xMinValue) * xStep
            val y = height - padding - (entry.value - yMinValue) * yStep
            if (index == 0) {
                path.moveTo(x, y)
            } else {
                path.lineTo(x, y)
            }
        }
        drawPath(
            path = path,
            color = Color.Blue,
            style = Stroke(
                width = 4f,
                cap = StrokeCap.Round,
                join = StrokeJoin.Round
            )
        )
    }
}
