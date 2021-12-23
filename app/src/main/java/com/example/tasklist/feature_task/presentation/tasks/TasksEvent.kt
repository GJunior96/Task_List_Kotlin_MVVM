package com.example.tasklist.feature_task.domain.util

import com.example.tasklist.feature_task.domain.model.Task

sealed class TasksEvent {
    data class Order(val taskOrder: TaskOrder): TasksEvent()
    data class DeleteTask(val task: Task): TasksEvent()
    data class ChangeTaskState(val value: Int): TasksEvent()
    object RestoreTask: TasksEvent()
    object ToggleOrderSection: TasksEvent()
    object ChangeDay: TasksEvent()
}
