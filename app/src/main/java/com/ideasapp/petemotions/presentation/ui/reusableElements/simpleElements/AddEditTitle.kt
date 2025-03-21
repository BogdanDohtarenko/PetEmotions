package com.ideasapp.petemotions.presentation.ui.reusableElements.simpleElements

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun AddEditTitle(item : Any?) {
    Text(text = if (item == null) "Add" else "Edit", textAlign = TextAlign.Center, modifier = Modifier)
}
