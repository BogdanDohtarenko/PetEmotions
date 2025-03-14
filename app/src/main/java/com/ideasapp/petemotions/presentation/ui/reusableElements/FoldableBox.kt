package com.ideasapp.petemotions.presentation.ui.reusableElements

import android.widget.ImageButton
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ideasapp.petemotions.presentation.ui.theme.MainTheme

@Composable
fun FoldableBox(
    contentForBox: @Composable () -> Unit,
) {
    var isExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .background(MainTheme.colors.spareContentColor)
            .padding(10.dp)
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            Image(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "expand",
                modifier = Modifier.clickable{ isExpanded = !isExpanded }
            )
            AnimatedVisibility(
                visible = isExpanded,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Column(modifier = Modifier.padding(top = 8.dp)) {
                    contentForBox()
                }
            }
        }
    }


}