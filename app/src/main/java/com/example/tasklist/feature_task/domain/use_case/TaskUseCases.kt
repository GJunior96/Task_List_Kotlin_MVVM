package com.example.tasklist.feature_task.domain.use_case

data class TaskUseCases(
    val getAllTasks: GetAllTasks,
    val getTasksByDate: GetTasksByDate,
    val deleteTask: DeleteTask,
    val addTask: AddTask,
    val getTask: GetTask
)
