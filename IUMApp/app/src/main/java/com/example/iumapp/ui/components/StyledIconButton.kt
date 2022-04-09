package com.example.iumapp.ui.components

import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun StyledIconButton(
    onClickFun: () -> Unit,
    textString: String,
    iconVector: ImageVector,
    iconDescription: String
) {
    ExtendedFloatingActionButton(
        backgroundColor = MaterialTheme.colors.primary,
        onClick = { onClickFun() },
        text = {
            Text(
                text = textString,
            )
        },
        icon = {
            Icon(
                imageVector = iconVector,
                contentDescription = iconDescription,
            )
        }
    )
}