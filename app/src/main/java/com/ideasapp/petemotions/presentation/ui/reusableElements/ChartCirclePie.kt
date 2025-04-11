package com.ideasapp.petemotions.presentation.ui.reusableElements

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ideasapp.petemotions.domain.entity.stastistics.ChartModel


@Composable
fun ChartCirclePie(
    modifier: Modifier,
    charts: List<ChartModel>,
    size: Dp = 200.dp,
    strokeWidth: Dp = 16.dp
) {
    val myText = "ChartCirclePie"
    val textMeasurer = rememberTextMeasurer()
    val textLayoutResult = textMeasurer.measure(text = AnnotatedString(myText))
    val textSize = textLayoutResult.size
    val density = LocalDensity.current

    Canvas(
        modifier = modifier
            .size(size)
            .padding(12.dp),
        onDraw = {
            var startAngle = 0f
            var sweepAngle: Float

            charts.forEach { chart ->
                sweepAngle = (chart.value / 100) * 360

                drawArc(
                    color = chart.color,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = Stroke(
                        width = strokeWidth.toPx(),
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round
                    )
                )

                // Update the start angle for the next segment, adding a gap if needed
                startAngle += sweepAngle
            }
        }
    )
}