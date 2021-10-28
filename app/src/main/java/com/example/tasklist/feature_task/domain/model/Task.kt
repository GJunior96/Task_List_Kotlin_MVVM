package com.example.tasklist.feature_task.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.lang.Exception

@Entity
data class Task(
    val content: String,
    val date: String,
    val hour: String,
    val state: String,
    @PrimaryKey val id: Int? = null
)

class InvalidTaskException(message: String): Exception(message)
