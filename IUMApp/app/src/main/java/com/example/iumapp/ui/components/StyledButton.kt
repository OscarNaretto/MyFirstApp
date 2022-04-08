package com.example.iumapp.ui.components

import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import org.w3c.dom.Text

@Composable
fun StyledButton(
    onClickFun: () -> Unit,
    textString: String,
    backgroundColor: Color = MaterialTheme.colors.primary
) {
    ExtendedFloatingActionButton(
        onClick = { onClickFun() },
        text = { Text(textString) },
        backgroundColor = backgroundColor,
    )
}