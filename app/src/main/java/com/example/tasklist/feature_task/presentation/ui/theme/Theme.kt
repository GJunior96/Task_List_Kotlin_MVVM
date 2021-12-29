package com.example.tasklist.feature_task.presentation.ui.theme

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.unit.dp
import com.example.tasklist.feature_task.presentation.ui.theme.attr.*
import com.example.tasklist.feature_task.presentation.ui.theme.attr.LocalElevations
import com.example.tasklist.feature_task.presentation.ui.theme.attr.LocalPaddings

@Composable
fun TaskListAppTheme(
    colorPalette: Colors,
    content: @Composable () -> Unit
) {

    CompositionLocalProvider(
        LocalPaddings provides Paddings(),
        LocalElevations provides Elevations(card = 8.dp)
    ) {
        MaterialTheme(
            colors = colorPalette,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}

object TaskListAppTheme {
    val colors: Colors
        @Composable
        get() = MaterialTheme.colors

    val typography: Typography
        @Composable
        get() = MaterialTheme.typography

    val shapes: Shapes
        @Composable
        get() = MaterialTheme.shapes

    val paddings: Paddings
        @Composable
        get() = LocalPaddings.current

    val elevations: Elevations
        @Composable
        get() = LocalElevations.current
}