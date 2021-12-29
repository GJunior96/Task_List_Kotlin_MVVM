package com.example.tasklist.feature_task.presentation.ui.theme

import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.example.tasklist.feature_task.presentation.ui.theme.attr.WhitePrimary
import com.example.tasklist.feature_task.presentation.ui.theme.attr.WhiteSecondary
import com.example.tasklist.feature_task.presentation.ui.theme.attr.WhiteTertiary

val WhiteColorPalette = lightColors(
    primary = WhitePrimary,
    onPrimary = WhiteTertiary,
    primaryVariant = WhiteSecondary,
    secondary = WhiteSecondary,
    onSecondary = WhiteTertiary,
    surface = WhiteTertiary,
    onSurface = WhitePrimary,
    background = WhiteTertiary,
    onBackground = WhitePrimary
)

@Composable
fun WhiteTheme(
    content: @Composable() () -> Unit
) {
    TaskListAppTheme(
        colorPalette = WhiteColorPalette,
        content = content
    )
}