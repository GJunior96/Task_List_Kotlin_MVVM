package com.example.tasklist.feature_task.domain.use_case

import android.util.Log
import com.example.tasklist.feature_task.domain.model.Task
import com.example.tasklist.feature_task.domain.repository.TaskRepository
import com.example.tasklist.feature_task.domain.util.OrderType
import com.example.tasklist.feature_task.domain.util.TaskOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTasks(
    private val repository: TaskRepository
) {
    operator fun invoke(
        taskOrder: TaskOrder = TaskOrder.Hour(OrderType.Ascending),
        taskDate: String
    ): Flow<List<Task>>? {
        return repository.getTasks(taskDate)?.map { tasks ->

            when(taskOrder.orderType) {
                is OrderType.Ascending -> {
                    when(taskOrder) {
                        is TaskOrder.Description -> tasks.sortedBy { it.content.lowercase() }
                        is TaskOrder.Hour -> tasks.sortedBy { it.hour }
                        is TaskOrder.State -> tasks.sortedBy { it.state }
                    }
                }
                is OrderType.Descending -> {
                    when(taskOrder) {
                        is TaskOrder.Description -> tasks.sortedByDescending { it.content.lowercase() }
                        is TaskOrder.Hour -> tasks.sortedByDescending { it.hour }
                        is TaskOrder.State -> tasks.sortedByDescending { it.state }
                    }
                }
            }
        }
    }
}