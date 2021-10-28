package com.example.tasklist.feature_task.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    val content: String,
    val date: String,
    val hour: String,
    val state: String,
    @PrimaryKey val id: Int? = null
)
