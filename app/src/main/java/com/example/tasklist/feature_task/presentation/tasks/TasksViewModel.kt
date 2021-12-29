package com.example.tasklist.feature_task.presentation.tasks

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasklist.feature_task.domain.model.InvalidTaskException
import com.example.tasklist.feature_task.domain.model.Task
import com.example.tasklist.feature_task.domain.use_case.TaskUseCases
import com.example.tasklist.feature_task.domain.util.OrderType
import com.example.tasklist.feature_task.domain.util.TaskOrder
import com.example.tasklist.feature_task.presentation.add_edit_task.AddEditTaskViewModel
import com.example.tasklist.feature_task.presentation.settings.SettingsViewModel
import com.example.tasklist.feature_task.presentation.settings.SwitchState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
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

    var date: String = getMonthAndDay()

    private val _eventFlow = MutableSharedFlow<AddEditTaskViewModel.UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getTasks(TaskOrder.Hour(OrderType.Ascending))
    }

    fun onEvent(event: TasksEvent) {
        when(event) {
            is TasksEvent.Order -> {
                if(state.value.taskOrder::class == event.taskOrder::class &&
                    state.value.taskOrder.orderType == event.taskOrder.orderType
                ) {
                    return
                }
                getTasks(event.taskOrder)
            }
            is TasksEvent.DeleteTask -> {
                viewModelScope.launch {
                    taskUseCases.deleteTask(event.task)
                    recentlyDeleteTask = event.task
                }
            }
            is TasksEvent.ChangeTaskState -> {
                viewModelScope.launch {
                    taskUseCases.getTask(event.value).also {
                        if (it?.state == "todo") {
                            try {
                                taskUseCases.addTask(
                                    Task(
                                        content = it.content,
                                        date = it.date,
                                        hour = it.hour,
                                        state = "done",
                                        id = it.id
                                    )
                                )
                                _eventFlow.emit(AddEditTaskViewModel.UiEvent.SaveTask)
                            } catch (e: InvalidTaskException) {
                                _eventFlow.emit(
                                    AddEditTaskViewModel.UiEvent.ShowSnackbar(
                                        message = e.message ?: "Couldn't change task state."
                                    )
                                )
                            }
                        }
                    }
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
            is TasksEvent.ChangeDay -> {
                getTasks(TaskOrder.Hour(OrderType.Ascending))
            }
        }
    }

    private fun getTasks(taskOrder: TaskOrder) {
        getTasksJob?.cancel()
        getTasksJob = taskUseCases.getTasksByDate(taskDate = date, taskOrder = taskOrder)
            ?.onEach { tasks ->
                _state.value = state.value.copy(
                    tasks = tasks,
                    taskOrder = taskOrder,
                )
            }
            ?.launchIn(viewModelScope)
    }

    private fun getMonthAndDay(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            current.format(formatter)
        } else {
            val date = Calendar.getInstance()
            val formatter = SimpleDateFormat("dd/MM")
            formatter.format(date.time)
        }
    }

    fun fillDaysList(month: Int): MutableList<WeekDay> {
        val formatter = SimpleDateFormat("EEE")
        val list = mutableListOf<WeekDay>()
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH, month)

        list.add(WeekDay("", -1, mutableStateOf(false)))
        list.add(WeekDay("", 0, mutableStateOf(false)))

        for (i in 0 until calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            calendar.set(Calendar.DAY_OF_MONTH, i + 1)
            list.add(WeekDay(formatter.format(calendar.time), i + 1, mutableStateOf(false)))
        }

        list.add(WeekDay("", -2, mutableStateOf(false)))
        list.add(WeekDay("", -3, mutableStateOf(false)))

        return list
    }
}