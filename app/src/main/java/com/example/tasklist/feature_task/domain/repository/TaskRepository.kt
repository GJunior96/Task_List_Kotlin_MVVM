package com.example.tasklist.feature_task.domain.repository

import com.example.tasklist.feature_task.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    fun getAllTasks(): Flow<List<Task>>

    fun getTasksByDate(date: String): Flow<List<Task>>?

    suspend fun getTaskById(id: Int): Task?

    suspend fun insertTask(task: Task)

    suspend fun deleteTask(task: Task)
}