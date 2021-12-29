package com.example.tasklist.feature_task.presentation.tasks

import com.example.tasklist.feature_task.domain.model.Task
import com.example.tasklist.feature_task.domain.util.TaskOrder

sealed class TasksEvent {
    data class Order(val taskOrder: TaskOrder): TasksEvent()
    data class DeleteTask(val task: Task): TasksEvent()
    data class ChangeTaskState(val value: Int): TasksEvent()
    object RestoreTask: TasksEvent()
    object ToggleOrderSection: TasksEvent()
    object ChangeDay: TasksEvent()
}
