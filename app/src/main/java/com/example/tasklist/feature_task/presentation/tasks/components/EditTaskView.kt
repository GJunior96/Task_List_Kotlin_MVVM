package com.example.tasklist.feature_task.presentation.tasks.components

import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.os.bundleOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.navigation.NavController
import com.example.tasklist.feature_task.domain.model.Task
import com.example.tasklist.feature_task.presentation.add_edit_task.AddEditTaskEvent
import com.example.tasklist.feature_task.presentation.add_edit_task.AddEditTaskViewModel
import com.example.tasklist.feature_task.presentation.add_edit_task.TaskTextFieldState
import com.example.tasklist.feature_task.presentation.add_edit_task.components.CustomButtomPicker
import com.example.tasklist.feature_task.presentation.add_edit_task.components.CustomTextField
import com.example.tasklist.feature_task.presentation.util.TimePicker
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

@ExperimentalComposeUiApi
@Composable
fun EditTaskView(
    task: MutableState<Task?>,
    viewModel: AddEditTaskViewModel = hiltViewModel(),
    onDismissRequest: () -> Unit,
    onClick: () -> Unit
) {
    val scope = rememberCoroutineScope()

    val description = remember { mutableStateOf(task.value?.content) }
    val date = remember { mutableStateOf(task.value?.date) }
    val hour = remember { mutableStateOf(task.value?.hour) }

    if (task.value != null) {
        viewModel.onEvent(AddEditTaskEvent.SelectedTask(task.value?.id))
        task.value = null
    }

    val expandedDateDialog = remember { mutableStateOf(false) }
    val expandedTimeDialog = remember { mutableStateOf(false) }

    val dateFormatter = SimpleDateFormat("dd/MM")

    if(expandedDateDialog.value) {
        DatePicker(
            onDateSelected = {
                viewModel.onEvent(AddEditTaskEvent.EnteredDate(dateFormatter.format(it.time)))
                date.value = dateFormatter.format(it.time)
            },
            onDismissRequest = { expandedDateDialog.value = false }
        )
    }

    if(expandedTimeDialog.value) {
        TimePicker(
            onTimeSelected = {
                viewModel.onEvent(AddEditTaskEvent.EnteredHour(it))
                hour.value = it
            },
            onDismissRequest = { expandedTimeDialog.value = false }
        )
    }

    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {

        Surface(
            color = MaterialTheme.colors.background.copy(alpha = 0.57f),
            modifier = Modifier
                .fillMaxSize()
                .clickable { onDismissRequest() }
        ) {
            Card(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(horizontal = 30.dp)
                    .clip(MaterialTheme.shapes.large)
                    .clickable {  },
                shape = MaterialTheme.shapes.large,
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Column(
                    modifier = Modifier.padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row() {
                        Text(text = "Edit Task")
                    }
                    Row() {
                        CustomTextField(
                            text = description.value ?: "",
                            hint = description.value ?: "",
                            onValueChange = { viewModel.onEvent(AddEditTaskEvent.EnteredDescription(it)) },
                            onFocusChange = { viewModel.onEvent(AddEditTaskEvent.ChangeDescriptionFocus(it)) },
                            isNewTask = false,
                            textStyle = MaterialTheme.typography.h3.copy(MaterialTheme.colors.background)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Yellow)
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            CustomButtomPicker(
                                text = date.value ?: "",
                                textStyle = MaterialTheme.typography.h3,
                                enabled = true,
                                isNewTask = false,
                                onClick = { expandedDateDialog.value = true }
                            )
                        }
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .background(Color.Red)
                        ) {
                            CustomButtomPicker(
                                text = hour.value ?: "",
                                textStyle = MaterialTheme.typography.h3,
                                enabled = true,
                                isNewTask = false,
                                onClick = {
                                    expandedTimeDialog.value = true
                                }
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .weight(1f)
                        ) {
                            TextButton(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        color = MaterialTheme.colors.background,
                                        shape = MaterialTheme.shapes.large
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = MaterialTheme.colors.background,
                                        shape = MaterialTheme.shapes.large
                                    ),
                                onClick = {
                                    viewModel.onEvent(AddEditTaskEvent.SaveTask)
                                    onDismissRequest()
                                }
                            ) {
                                Text(
                                    modifier = Modifier.padding(8.dp),
                                    text = "Save",
                                    color = MaterialTheme.colors.primary,
                                    style = MaterialTheme.typography.h3
                                )
                            }
                        }
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .weight(1f)
                        ) {
                            TextButton(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colors.primary),
                                onClick = onDismissRequest
                            ) {
                                Text(
                                    modifier = Modifier.padding(8.dp),
                                    text = "Cancel",
                                    color = MaterialTheme.colors.background,
                                    style = MaterialTheme.typography.h3
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}