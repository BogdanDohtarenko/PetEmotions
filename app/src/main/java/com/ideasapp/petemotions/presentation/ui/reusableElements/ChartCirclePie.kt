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
    modifier:Modifier,
    charts: List<ChartModel>,
    size:Dp = 200.dp,
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
            .background(Color.LightGray)
            .padding(12.dp),
        onDraw = {
            var startAngle = 0f
            var sweepAngle = 0f

            charts.forEach {
                val brush = createStripeBrush(
                    density = density,
                    stripeColor = Color.Red
                )

                sweepAngle = (it.value / 100) * 360

                drawArc(
                    color = it.color,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = Stroke(
                        width = strokeWidth.toPx(),
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round
                    )
                )
                startAngle += sweepAngle
            }

            drawText(
                textMeasurer, myText,
                topLeft = Offset(
                    (this.size.width - textSize.width) / 2f,
                    (this.size.height - textSize.height) / 2f
                ),
            )
        }
    )
}

fun createStripeBrush(
    density: Density,
    stripeColor: Color,
    stripeWidth: Dp = 2.dp,
    stripeToGapRatio: Float = 2f
): Brush {
    val stripeWidthPx = with(density) { stripeWidth.toPx() }
    val gapWidthPx = stripeWidthPx / stripeToGapRatio

    return Brush.horizontalGradient(
        listOf(
            stripeColor,
            stripeColor,
            Color.Transparent,
            Color.Transparent
        ),
        startX = 0f,
        endX = stripeWidthPx + gapWidthPx,
        tileMode = TileMode.Repeated
    )
}