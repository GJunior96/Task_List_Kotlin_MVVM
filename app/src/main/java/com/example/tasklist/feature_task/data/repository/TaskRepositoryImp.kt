package com.example.tasklist.feature_task.data.repository

import com.example.tasklist.feature_task.data.data_source.TaskDao
import com.example.tasklist.feature_task.domain.model.Task
import com.example.tasklist.feature_task.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class TaskRepositoryImp(
    private val dao: TaskDao
) : TaskRepository {
    override fun getTasks(date: String): Flow<List<Task>>? {
        return dao.getTasks(date)
    }

    override suspend fun getTaskById(id: Int): Task? {
        return dao.getTaskById(id)
    }

    override suspend fun insertTask(task: Task) {
        dao.insertTask(task)
    }

    override suspend fun deleteTask(task: Task) {
       dao.deleteTask(task)
    }
}