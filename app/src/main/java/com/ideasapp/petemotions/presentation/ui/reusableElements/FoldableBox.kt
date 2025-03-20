package com.ideasapp.petemotions.presentation.ui.reusableElements

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ideasapp.petemotions.presentation.ui.theme.MainTheme

// example of ideal compose function :)
@Composable
fun FoldableBox(
    titleText: String,
    modifier : Modifier = Modifier,
    isExpandedByDefault: Boolean = false,
    contentForBox: @Composable () -> Unit,
) {
    var isExpanded by remember { mutableStateOf(isExpandedByDefault) }

    Box(
        modifier = modifier
            .wrapContentHeight()
            .clip(shape = RoundedCornerShape(10.dp))
            .background(MainTheme.colors.spareContentColor)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 5.dp)
            ) {
                Image(
                    imageVector = if (!isExpanded) Icons.Default.KeyboardArrowDown else  Icons.Default.KeyboardArrowUp,
                    contentDescription = "expand",
                    modifier = modifier.clickable{ isExpanded = !isExpanded }
                )
                Text(text = titleText, modifier = modifier.padding(horizontal = 10.dp), fontSize = 16.sp)
            }
            AnimatedVisibility(
                visible = isExpanded,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Column(modifier = modifier.padding(top = 8.dp)) {
                    contentForBox()
                }
            }
        }
    }


}