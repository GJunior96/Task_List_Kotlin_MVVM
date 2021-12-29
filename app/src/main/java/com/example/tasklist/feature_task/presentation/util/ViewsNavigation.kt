package com.example.tasklist.feature_task.presentation.util

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.example.tasklist.feature_task.presentation.add_edit_task.AddTaskScreen
import com.example.tasklist.feature_task.presentation.settings.SettingsScreen
import com.example.tasklist.feature_task.presentation.tasks.TaskScreen

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun ViewsNavigation(
    navController: NavHostController,
    themeType: MutableState<String?>,
    isChangingTheme: MutableState<Boolean>,
    navState: MutableState<Bundle?>
) {
    NavHost(navController = navController,
        startDestination = Screen.TasksScreen.route
    ) {
        composable(
            route = Screen.TasksScreen.route
        ) {
            TaskScreen(navController = navController)
        }
        composable(
            route = Screen.AddTaskScreen.route
        ) {
            AddTaskScreen(navController = navController)
        }
        composable(route = Screen.SettingsScreen.route
        ) {
            SettingsScreen(
                navController = navController,
                themeType = themeType,
                isChangingTheme = isChangingTheme,
                navState = navState
            )
        }
        if (isChangingTheme.value) {
            navController.restoreState(navState = navState.value)
        }
    }
}