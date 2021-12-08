package com.example.tasklist.feature_task.presentation.ui.theme

import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val PurpleColorPalette = lightColors(
    primary = DarkPurple,
    onPrimary = LightPurple,
    primaryVariant = Gray,
    secondary = Gray,
    onSecondary = LightPurple,
    surface = LightPurple,
    onSurface = DarkPurple,
    background = LightPurple,
    onBackground = DarkPurple
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