package com.example.tasklist.feature_task.presentation.tasks

import androidx.compose.runtime.MutableState

data class WeekDay(val weekDay: String, val day: Int, var clicked: MutableState<Boolean>)
