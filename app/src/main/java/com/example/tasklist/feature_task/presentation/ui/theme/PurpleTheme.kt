package com.example.tasklist.feature_task.presentation.ui.theme

import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.example.tasklist.feature_task.presentation.ui.theme.attr.*

val PurpleColorPalette = lightColors(
    primary = PurplePrimary,
    onPrimary = PurpleTertiary,
    primaryVariant = PurpleSecondary,
    secondary = PurpleSecondary,
    onSecondary = PurpleTertiary,
    surface = PurpleTertiary,
    onSurface = PurplePrimary,
    background = PurpleTertiary,
    onBackground = PurplePrimary
)

@Composable
fun PurpleTheme(
    content: @Composable() () -> Unit
) {
    TaskListAppTheme(
        colorPalette = PurpleColorPalette,
        content = content
    )
}
