package com.example.tasklist.feature_task.presentation.add_edit_task

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.example.tasklist.feature_task.domain.model.InvalidTaskException
import com.example.tasklist.feature_task.domain.model.Task
import com.example.tasklist.feature_task.domain.use_case.TaskUseCases
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTaskViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases,
): ViewModel() {
    private val _taskDescription = mutableStateOf(TaskTextFieldState(
        hint = "Ex: Take out the garbage"
    ))
    val taskDescription: State<TaskTextFieldState> = _taskDescription

    private val _taskDate = mutableStateOf(TaskTextFieldState(
        hint = "24/11"
    ))
    val taskDate: State<TaskTextFieldState> = _taskDate

    private val _taskHour = mutableStateOf(TaskTextFieldState(
        hint = "13:40"
    ))
    val taskHour: State<TaskTextFieldState> = _taskHour

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentTaskId: Int? = null

    fun onEvent(event: AddEditTaskEvent) {
        when(event) {
            is AddEditTaskEvent.SelectedTask -> {
                if (event.value != null) {
                    viewModelScope.launch {
                        taskUseCases.getTask(event.value)?.also { task ->
                            currentTaskId = task.id
                            _taskDescription.value = taskDescription.value.copy(
                                text = task.content,
                                isHintVisible = false
                            )
                            _taskDate.value = taskDate.value.copy(
                                text = task.date,
                                isHintVisible = false
                            )
                            _taskHour.value = taskHour.value.copy(
                                text = task.hour,
                                isHintVisible = false
                            )
                        }
                    }
                }
            }
            is AddEditTaskEvent.EnteredDescription -> {
                _taskDescription.value = taskDescription.value.copy(
                    text = event.value
                )
            }
            is AddEditTaskEvent.ChangeDescriptionFocus -> {
                _taskDescription.value = taskDescription.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            taskDescription.value.text.isBlank()
                )
            }
            is AddEditTaskEvent.EnteredDate -> {
                _taskDate.value = taskDate.value.copy(
                    text = event.value
                )
            }
            is AddEditTaskEvent.ChangeDateFocus -> {
                _taskDate.value = taskDate.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            taskDate.value.text.isBlank()
                )
            }
            is AddEditTaskEvent.EnteredHour -> {
                _taskHour.value = taskHour.value.copy(
                    text = event.value
                )
            }
            is AddEditTaskEvent.ChangeHourFocus -> {
                _taskHour.value = taskHour.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            taskHour.value.text.isBlank()
                )
            }
            is AddEditTaskEvent.SaveTask -> {
                viewModelScope.launch {
                    try {
                        taskUseCases.addTask(
                            Task(
                                content = taskDescription.value.text,
                                date = taskDate.value.text,
                                hour = taskHour.value.text,
                                state = "todo",
                                id = currentTaskId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveTask)
                    } catch (e: InvalidTaskException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save task"
                            )
                        )
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object SaveTask: UiEvent()
    }
}