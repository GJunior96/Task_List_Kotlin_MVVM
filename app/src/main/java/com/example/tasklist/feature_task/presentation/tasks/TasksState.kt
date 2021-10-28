package com.example.tasklist.feature_task.presentation.tasks

import com.example.tasklist.feature_task.domain.model.Task
import com.example.tasklist.feature_task.domain.util.OrderType
import com.example.tasklist.feature_task.domain.util.TaskOrder

data class TasksState(
    val tasks: List<Task> = emptyList(),
    val taskOrder: TaskOrder = TaskOrder.Hour(OrderType.Ascending),
    val isOrderSectionVisible: Boolean = false
)