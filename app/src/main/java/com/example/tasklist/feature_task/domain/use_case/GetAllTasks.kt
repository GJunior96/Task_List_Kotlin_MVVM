package com.example.tasklist.feature_task.domain.use_case

import com.example.tasklist.feature_task.domain.model.Task
import com.example.tasklist.feature_task.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class GetAllTasks(
    private val repository: TaskRepository
) {
    operator fun invoke(): Flow<List<Task>> {
        return repository.getAllTasks()
    }
}