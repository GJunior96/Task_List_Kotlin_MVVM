package com.example.tasklist.feature_task.presentation.add_edit_task

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tasklist.R
import com.example.tasklist.feature_task.presentation.add_edit_task.components.CustomButtonPicker
import com.example.tasklist.feature_task.presentation.add_edit_task.components.CustomTextField
import com.example.tasklist.feature_task.presentation.tasks.components.CustomTopBarView
import com.example.tasklist.feature_task.presentation.util.DatePicker
import com.example.tasklist.feature_task.presentation.util.TimePicker
import com.example.tasklist.feature_task.presentation.util.dayAndMonth
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddTaskScreen(
    navController: NavController,
    viewModel: AddEditTaskViewModel = hiltViewModel(),
) {
    val descriptionState = viewModel.taskDescription.value
    val dateState = viewModel.taskDate.value
    val hourState = viewModel.taskHour.value

    val expandedTimeDialog = remember { mutableStateOf(false) }
    val expandedDateDialog = remember { mutableStateOf(false) }

    val scaffoldState = rememberScaffoldState()

    if(expandedDateDialog.value) {
        DatePicker(
            onDateSelected = {
                viewModel.onEvent(AddEditTaskEvent.EnteredDate(it.time.dayAndMonth()))
            },
            onDismissRequest = { expandedDateDialog.value = false },
            isNewTask = true
        )
    }

    if(expandedTimeDialog.value) {
        TimePicker(
            onTimeSelected = {
                viewModel.onEvent(AddEditTaskEvent.EnteredHour(it))
            },
            onDismissRequest = { expandedTimeDialog.value = false }
        )
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is AddEditTaskViewModel.UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is AddEditTaskViewModel.UiEvent.SaveTask -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { CustomTopBarView(
            navController = navController,
            stringResource(R.string.add_task_screen_toolbar_title)
        ) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                CustomTextField(
                    text = descriptionState.text,
                    hint = descriptionState.hint,
                    label = stringResource(R.string.task_description),
                    onValueChange = { viewModel.onEvent(AddEditTaskEvent.EnteredDescription(it)) },
                    onFocusChange = { viewModel.onEvent(AddEditTaskEvent.ChangeDescriptionFocus(it)) },
                    textStyle = MaterialTheme.typography.h3
                )
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    CustomButtonPicker(
                        text = if(dateState.text.isBlank()) dateState.hint else dateState.text,
                        label = stringResource(R.string.task_date),
                        textStyle = MaterialTheme.typography.h3,
                        enabled = true,
                        onClick = { expandedDateDialog.value = true }
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    CustomButtonPicker(
                        text = if(hourState.text.isBlank()) hourState.hint else hourState.text,
                        label = stringResource(R.string.task_hour),
                        textStyle = MaterialTheme.typography.h3,
                        enabled = true,
                        onClick = { expandedTimeDialog.value = true }
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    shape = MaterialTheme.shapes.large,
                    onClick = { viewModel.onEvent(AddEditTaskEvent.SaveTask) }
                ) {
                    Text(
                        text = stringResource(R.string.add_new_task),
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onPrimary
                    )
                }
            }
        }
    }
}