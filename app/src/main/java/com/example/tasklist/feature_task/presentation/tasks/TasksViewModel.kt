package com.example.tasklist.feature_task.presentation.tasks

import android.os.Build
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasklist.feature_task.domain.model.Task
import com.example.tasklist.feature_task.domain.use_case.TaskUseCases
import com.example.tasklist.feature_task.domain.util.OrderType
import com.example.tasklist.feature_task.domain.util.TaskOrder
import com.example.tasklist.feature_task.domain.util.TasksEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases
) : ViewModel() {

    private val _state = mutableStateOf(TasksState())
    val state: State<TasksState> = _state

    private var recentlyDeleteTask: Task? = null

    private var getTasksJob: Job? = null

    init {
        getTasks(TaskOrder.Hour(OrderType.Ascending), getDate())
    }

    fun onEvent(event: TasksEvent) {
        when(event) {
            is TasksEvent.Order -> {
                if(state.value.taskOrder::class == event.taskOrder::class &&
                    state.value.taskOrder.orderType == event.taskOrder.orderType
                ) {
                    return
                }
                getTasks(event.taskOrder, event.task.date)
            }
            is TasksEvent.DeleteTask -> {
                viewModelScope.launch {
                    taskUseCases.deleteTask(event.task)
                    recentlyDeleteTask = event.task
                }
            }
            is TasksEvent.RestoreTask -> {
                viewModelScope.launch {
                    taskUseCases.addTask(recentlyDeleteTask ?: return@launch)
                    recentlyDeleteTask = null
                }
            }
            is TasksEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getTasks(taskOrder: TaskOrder, taskDate: String) {
        getTasksJob?.cancel()
        getTasksJob = taskUseCases.getTasks(taskOrder, taskDate)
            ?.onEach { tasks ->
                _state.value = state.value.copy(
                    tasks = tasks,
                    taskOrder = taskOrder,
                )
            }
            ?.launchIn(viewModelScope)
    }

    private fun getDate(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            current.format(formatter)
        } else {
            val date = Date()
            val formatter = SimpleDateFormat("dd/MM/yyyy")
            formatter.format(date)
        }
    }
}