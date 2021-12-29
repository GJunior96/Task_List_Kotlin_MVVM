 package com.example.tasklist

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.compiler.plugins.kotlin.ComposeFqNames.remember
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.tasklist.feature_task.presentation.add_edit_task.AddTaskScreen
import com.example.tasklist.feature_task.presentation.settings.SELECTED_THEME
import com.example.tasklist.feature_task.presentation.settings.SHARED_PREFS
import com.example.tasklist.feature_task.presentation.settings.SettingsScreen
import com.example.tasklist.feature_task.presentation.tasks.TaskScreen
import com.example.tasklist.feature_task.presentation.ui.theme.*
import com.example.tasklist.feature_task.presentation.ui.theme.PurpleColorPalette
import com.example.tasklist.feature_task.presentation.util.Screen
import com.example.tasklist.feature_task.presentation.util.ThemeType.*
import com.example.tasklist.feature_task.presentation.util.ViewsNavigation
import dagger.hilt.android.AndroidEntryPoint

 @AndroidEntryPoint
 class MainActivity : ComponentActivity() {
    @ExperimentalComposeUiApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val sharedPrefs = this.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)
            val themeType = remember { mutableStateOf(sharedPrefs.getString(SELECTED_THEME, "purple")) }
            val isChangingTheme = remember { mutableStateOf(false) }
            val navState: MutableState<Bundle?> = remember { mutableStateOf(null) }
            val navController = rememberNavController()

            val themeFunction: @Composable (
                content: @Composable () -> Unit
            ) -> Unit =
                when (themeType.value) {
                    "purple" -> {
                        content -> PurpleTheme(content)
                        this.window.statusBarColor = ContextCompat.getColor(this, R.color.purple_primary)
                    }
                    "black" -> {
                        content -> BlackTheme(content)
                        this.window.statusBarColor = ContextCompat.getColor(this, R.color.black_tertiary)
                    }
                    "blue" -> {
                        content -> BlueTheme(content)
                        this.window.statusBarColor = ContextCompat.getColor(this, R.color.blue_primary)
                    }
                    "white" -> {
                        content -> WhiteTheme(content)
                        this.window.statusBarColor = ContextCompat.getColor(this, R.color.white_primary)
                    }
                    else -> { content -> PurpleTheme(content) }
                }

            themeFunction.invoke {
                ViewsNavigation(
                    navController = navController,
                    themeType = themeType,
                    isChangingTheme = isChangingTheme,
                    navState = navState
                )
            }
        }
    }
}