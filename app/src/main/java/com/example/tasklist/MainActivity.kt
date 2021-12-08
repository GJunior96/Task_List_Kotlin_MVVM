 package com.example.tasklist

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.compiler.plugins.kotlin.ComposeFqNames.remember
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.core.view.WindowCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.tasklist.feature_task.presentation.add_edit_task.AddTaskScreen
import com.example.tasklist.feature_task.presentation.tasks.TaskScreen
import com.example.tasklist.feature_task.presentation.ui.theme.PurpleTheme
import com.example.tasklist.feature_task.presentation.ui.theme.TaskListAppTheme
import com.example.tasklist.feature_task.presentation.util.Screen
import com.example.tasklist.feature_task.presentation.util.ThemeType.*
import dagger.hilt.android.AndroidEntryPoint

 @AndroidEntryPoint
 class MainActivity : ComponentActivity() {
    @ExperimentalComposeUiApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val widthPx = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(widthPx)

        val widthDp = resources.displayMetrics.run { widthPixels / density }
        setContent {
            /*TaskListAppTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ) {


                }
            }*/
            val themeType = remember { mutableStateOf(PURPLE) }

            val themeFunction: @Composable (
                content: @Composable () -> Unit
            ) -> Unit =
                when (themeType.value) {
                    PURPLE -> { content -> PurpleTheme(content) }
                    BLACK -> { content -> PurpleTheme(content) }
                    BLUE -> { content -> PurpleTheme(content) }
                    WHITE -> { content -> PurpleTheme(content) }
                }

            themeFunction.invoke {
                val navController = rememberNavController()
                NavHost(navController = navController,
                    startDestination = Screen.TasksScreen.route
                ) {
                    composable(route = Screen.TasksScreen.route) {
                        TaskScreen(
                            navController = navController,
                            screenWidthDp = widthDp,
                            screenWidthPx = widthPx.widthPixels
                        )
                    }
                    composable(
                        route = Screen.AddTaskScreen.route +
                                "?taskId={taskId}",
                        arguments = listOf(
                            navArgument(
                                name = "taskId"
                            ) {
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )
                    ) {
                        AddTaskScreen(navController = navController, screenWidthDp = widthDp)
                    }
                }
            }

            /*val navController = rememberNavController()
            NavHost(navController = navController,
                startDestination = Screen.TasksScreen.route
            ) {
                composable(route = Screen.TasksScreen.route) {
                    TaskScreen(navController = navController)
                }
            }*/
        }
    }
}