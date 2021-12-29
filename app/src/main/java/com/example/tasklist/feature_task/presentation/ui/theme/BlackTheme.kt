package com.example.tasklist.feature_task.presentation.ui.theme

import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.example.tasklist.feature_task.presentation.ui.theme.attr.BlackPrimary
import com.example.tasklist.feature_task.presentation.ui.theme.attr.BlackSecondary
import com.example.tasklist.feature_task.presentation.ui.theme.attr.BlackTertiary

val BlackColorPalette = lightColors(
    primary = BlackPrimary,
    onPrimary = BlackTertiary,
    primaryVariant = BlackSecondary,
    secondary = BlackSecondary,
    onSecondary = BlackTertiary,
    surface = BlackTertiary,
    onSurface = BlackPrimary,
    background = BlackTertiary,
    onBackground = BlackPrimary
)

@Composable
fun BlackTheme(
    content: @Composable() () -> Unit
) {
    TaskListAppTheme(
        colorPalette = BlackColorPalette,
        content = content
    )
}