package com.example.tasklist.feature_task.presentation.util

sealed class Screen(val route: String) {
    object TasksScreen: Screen("tasks_screen")
    object AddTaskScreen: Screen("add_task_screen")
}
