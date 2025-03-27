package com.ideasapp.petemotions.presentation.ui.reusableElements

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.Alignment
import android.graphics.PointF
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

// Constants for curve calculations
private const val CURVE_CIRCLE_RADIUS = 46

// Coordinates for the first and second curves
private val mFirstCurveStartPoint = PointF()
private val mFirstCurveControlPoint1 = PointF()
private val mFirstCurveControlPoint2 = PointF()
private val mFirstCurveEndPoint = PointF()
private val mSecondCurveControlPoint1 = PointF()
private val mSecondCurveControlPoint2 = PointF()
private var mSecondCurveStartPoint = PointF()
private var mSecondCurveEndPoint = PointF()

// Custom shape for the bottom bar
class CustomBottomBarShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(path = Path().apply {
            val curveDepth = (CURVE_CIRCLE_RADIUS / 1.5).toFloat()

            // Start point before the first curve
            mFirstCurveStartPoint.set(
                (size.width / 2) - (CURVE_CIRCLE_RADIUS * 1.8).toFloat(),
                curveDepth
            )

            // End point after the first curve
            mFirstCurveEndPoint.set(
                size.width / 2,
                0F
            )

            // Start and end points for the second curve
            mSecondCurveStartPoint = mFirstCurveEndPoint
            mSecondCurveEndPoint.set(
                (size.width / 2) + (CURVE_CIRCLE_RADIUS * 1.8).toFloat(),
                curveDepth
            )

            // Control points for the first curve
            mFirstCurveControlPoint1.set(
                mFirstCurveStartPoint.x + curveDepth,
                mFirstCurveStartPoint.y
            )
            mFirstCurveControlPoint2.set(
                mFirstCurveEndPoint.x - (CURVE_CIRCLE_RADIUS * 2.1).toFloat() + CURVE_CIRCLE_RADIUS,
                mFirstCurveEndPoint.y
            )

            // Control points for the second curve
            mSecondCurveControlPoint1.set(
                mSecondCurveStartPoint.x + (CURVE_CIRCLE_RADIUS * 2.1).toFloat() - CURVE_CIRCLE_RADIUS,
                mSecondCurveStartPoint.y
            )
            mSecondCurveControlPoint2.set(
                mSecondCurveEndPoint.x - curveDepth,
                mSecondCurveEndPoint.y
            )

            // Draw the path
            moveTo(0f, curveDepth)
            lineTo(mFirstCurveStartPoint.x, mFirstCurveStartPoint.y)
            cubicTo(
                mFirstCurveControlPoint1.x, mFirstCurveControlPoint1.y,
                mFirstCurveControlPoint2.x, mFirstCurveControlPoint2.y,
                mFirstCurveEndPoint.x, mFirstCurveEndPoint.y
            )
            cubicTo(
                mSecondCurveControlPoint1.x, mSecondCurveControlPoint1.y,
                mSecondCurveControlPoint2.x, mSecondCurveControlPoint2.y,
                mSecondCurveEndPoint.x, mSecondCurveEndPoint.y
            )
            lineTo(size.width, curveDepth)
            lineTo(size.width, size.height)
            lineTo(0F, size.height)
            close()
        })
    }
}

@Composable
fun CustomBottomNavigationBar() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp), // Adjust height of the bottom bar
        color = MaterialTheme.colors.primary,
        shape = CustomBottomBarShape()
    ) {
        // Bottom bar content (e.g., navigation items)
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /* Handle click */ }) {
                Icon(Icons.Default.Home, contentDescription = "Home")
            }
            IconButton(onClick = { /* Handle click */ }) {
                Icon(Icons.Default.Search, contentDescription = "Search")
            }
            IconButton(onClick = { /* Handle click */ }) {
                Icon(Icons.Default.AccountCircle, contentDescription = "Profile")
            }
        }
    }
}

@Composable
fun CustomScaffold() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { CustomBottomNavigationBar() },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // Main content goes here
                Text(text = "Main Content", modifier = Modifier.align(Alignment.Center))
            }
        }
    )
}

@Composable
fun App() {
    MaterialTheme {
        CustomScaffold()
    }
}