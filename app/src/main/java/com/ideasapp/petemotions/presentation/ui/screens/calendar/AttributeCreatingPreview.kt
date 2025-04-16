package com.ideasapp.petemotions.presentation.ui.screens.calendar

import android.icu.text.CaseMap.Title
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.sp
import com.ideasapp.petemotions.presentation.ui.theme.MainTheme

@Composable
fun AttributeCreatingPreview(
    title : String,
    imageVector : Int,
    modifier : Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Image(imageVector = ImageVector.vectorResource(id = imageVector), contentDescription = title, modifier = modifier)
        Text(text = title, color = MainTheme.colors.singleTheme, fontSize = 16.sp, modifier = modifier)
    }
}