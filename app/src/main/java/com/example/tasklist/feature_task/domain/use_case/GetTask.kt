package com.example.tasklist.feature_task.domain.use_case

import com.example.tasklist.feature_task.domain.model.Task
import com.example.tasklist.feature_task.domain.repository.TaskRepository

class GetTask(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(id: Int): Task? {
        return repository.getTaskById(id)
    }
}