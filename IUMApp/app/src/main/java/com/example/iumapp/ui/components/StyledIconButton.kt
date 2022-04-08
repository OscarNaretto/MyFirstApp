package com.example.iumapp.ui.components

import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun StyledIconButton(
    onClickFun: () -> Unit,
    textString: String,
    textColor: Color = Color.Unspecified,
    backgroundColor: Color = MaterialTheme.colors.primary,
    iconVector: ImageVector,
    iconDescription: String
) {
    ExtendedFloatingActionButton(
        onClick = { onClickFun() },
        text = {
            Text(
                text = textString,
                color = textColor
            )
        },
        backgroundColor = backgroundColor,
        icon = {
            Icon(
                imageVector = iconVector,
                contentDescription = iconDescription,
            )
        }
    )
}