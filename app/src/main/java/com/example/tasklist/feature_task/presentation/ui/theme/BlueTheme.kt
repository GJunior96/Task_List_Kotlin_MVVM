package com.example.tasklist.feature_task.presentation.ui.theme

import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.example.tasklist.feature_task.presentation.ui.theme.attr.*

val BlueColorPalette = lightColors(
    primary = BluePrimary,
    onPrimary = BlueTertiary,
    primaryVariant = BlueSecondary,
    secondary = BlueSecondary,
    onSecondary = BlueTertiary,
    surface = BlueTertiary,
    onSurface = BluePrimary,
    background = BlueTertiary,
    onBackground = BluePrimary
)

@Composable
fun BlueTheme(
    content: @Composable() () -> Unit
) {
    TaskListAppTheme(
        colorPalette = BlueColorPalette,
        content = content
    )
}
