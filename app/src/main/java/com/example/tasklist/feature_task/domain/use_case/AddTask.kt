package com.example.tasklist.feature_task.domain.use_case

import com.example.tasklist.feature_task.domain.model.InvalidTaskException
import com.example.tasklist.feature_task.domain.model.Task
import com.example.tasklist.feature_task.domain.repository.TaskRepository
import kotlin.jvm.Throws

class AddTask(
    private val repository: TaskRepository
) {
    @Throws(InvalidTaskException::class)
    suspend operator fun invoke(task: Task) {
        if(task.content.isBlank()) {
            throw InvalidTaskException("The description of the task can't be empty.")
        }
        if(task.date.isBlank()) {
            throw InvalidTaskException("The date of the task can't be empty.")
        }
        if(task.hour.isBlank()) {
            throw InvalidTaskException("The hour of the task can't be empty.")
        }
        repository.insertTask(task)
    }

}