package com.example.tasklist.feature_task.presentation.tasks

import android.content.Context.MODE_PRIVATE
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tasklist.R
import com.example.tasklist.feature_task.domain.model.Task
import com.example.tasklist.feature_task.presentation.settings.SELECTED_THEME
import com.example.tasklist.feature_task.presentation.settings.SHARED_PREFS
import com.example.tasklist.feature_task.presentation.settings.SHOW_COMPLETED_TASKS
import com.example.tasklist.feature_task.presentation.tasks.components.*
import com.example.tasklist.feature_task.presentation.util.DatePicker
import com.example.tasklist.feature_task.presentation.util.Screen
import com.example.tasklist.feature_task.presentation.util.dayAndMonth
import com.example.tasklist.feature_task.presentation.util.month
import kotlinx.coroutines.launch
import java.util.*

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun TaskScreen(
    navController: NavController,
    viewModel: TasksViewModel = hiltViewModel(),
) {
    val configs = LocalConfiguration.current
    val density = configs.densityDpi
    val screenWidthDp = configs.screenWidthDp.dp
    val screenWidthPx = ((screenWidthDp.value * density) / 160)

    val context = LocalContext.current
    val sharedPref = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)

    val state = viewModel.state.value
    val showCompletedTasks =
        remember { mutableStateOf(sharedPref.getBoolean(SHOW_COMPLETED_TASKS, true)) }
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val calendar = Calendar.getInstance()
    val expandedCalendarDialog = remember { mutableStateOf(false) }
    val month = remember { mutableStateOf(calendar) }
    val listDays =
        remember { mutableStateOf(viewModel.fillDaysList(month.value.get(Calendar.MONTH))) }

    val listState = rememberLazyListState()

    val pointer = remember { mutableStateOf(month.value.get(Calendar.DAY_OF_MONTH)) }

    val expandedTaskMenu = remember { mutableStateOf(false) }
    val expandedEditTask = remember { mutableStateOf(false) }
    val pickedTaskId = remember { mutableStateOf<Task?>(null) }

    if (expandedTaskMenu.value) {
        TaskOptionsMenu(
            onDismissRequest = { expandedTaskMenu.value = false },
            onClick = {
                when (it) {
                    "edit" -> {
                        expandedEditTask.value = true
                    }
                    "delete" -> {
                        pickedTaskId.value?.let { task ->
                            viewModel.onEvent(TasksEvent.DeleteTask(task))
                        }
                        expandedTaskMenu.value = false
                    }
                    "done" -> {
                        pickedTaskId.value?.id?.let { id ->
                            viewModel.onEvent(TasksEvent.ChangeTaskState(id))
                        }
                        expandedTaskMenu.value = false
                    }
                }
            }
        )
    }

    if (expandedEditTask.value) {
        EditTaskView(
            task = remember { mutableStateOf(pickedTaskId.value) },
            onDismissRequest = { expandedEditTask.value = false }
        )
    }

    if (expandedCalendarDialog.value) {
        DatePicker(
            onDateSelected = {
                month.value = it
                listDays.value.clear()
                listDays.value = viewModel.fillDaysList(it.get(Calendar.MONTH))
                viewModel.date = it.time.dayAndMonth()
                viewModel.onEvent(TasksEvent.ChangeDay)
            },
            onDismissRequest = { expandedCalendarDialog.value = false },
            isNewTask = false
        )
    }

    Scaffold(
        topBar = { CustomTopBarView(
            navController = navController,
            stringResource(R.string.task_screen_toolbar_title)
        ) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.AddTaskScreen.route) },
                backgroundColor = MaterialTheme.colors.primary,
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    stringResource(R.string.floating_button_description)
                )
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 46.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextButton(
                        onClick = { expandedCalendarDialog.value = true }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            modifier = Modifier
                                .size(20.dp)
                                .align(Alignment.CenterVertically),
                            contentDescription = stringResource(R.string.dropdown_arrow_description)
                        )
                        Text(
                            text = month.value.time.month(),
                            style = MaterialTheme.typography.h4,
                            color = MaterialTheme.colors.onBackground,
                            textAlign = Center
                        )
                    }
                }
                Column(modifier = Modifier.wrapContentSize()) {
                    IconButton(onClick = { viewModel.onEvent(TasksEvent.ToggleOrderSection) }) {
                        Icon(
                            imageVector = Icons.Default.Sort,
                            modifier = Modifier.size(30.dp),
                            tint = MaterialTheme.colors.onBackground,
                            contentDescription = stringResource(R.string.sort_icon)
                        )
                    }
                }
            }
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    taskOrder = state.taskOrder,
                    onOrderChange = { viewModel.onEvent(TasksEvent.Order(it)) }
                )
            }

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                state = listState,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(listDays.value, key = { it.day }) { item ->
                    CustomWeekDayView(
                        item,
                        modifier = Modifier
                            .clickable {
                                month.value.set(Calendar.DAY_OF_MONTH, item.day)
                                viewModel.date = month.value.time.dayAndMonth()
                                viewModel.onEvent(TasksEvent.ChangeDay)
                                pointer.value = item.day
                            },
                        emptyBoxModifier = Modifier
                            .size(width = ((screenWidthDp / 4) - 29.25.dp), height = 2.dp)
                            .background(Color.Red),
                        pointer = pointer
                    )
                }

                scope.launch {
                    pointer.value = month.value.get(Calendar.DAY_OF_MONTH)
                    when (month.value.get(Calendar.DAY_OF_MONTH)) {
                        (listDays.value.size - 5) -> {
                            listState.scrollToItem(month.value.get(Calendar.DAY_OF_MONTH))
                            listState.scrollBy(((-screenWidthPx / 2) + 376))
                        }
                        (listDays.value.size - 4) -> {
                            listState.scrollToItem(month.value.get(Calendar.DAY_OF_MONTH) - 1)
                            listState.scrollBy(((-screenWidthPx / 2) + 640))
                        }
                        else -> {
                            listState.scrollToItem(month.value.get(Calendar.DAY_OF_MONTH) + 1)
                            listState.scrollBy(((-screenWidthPx / 2) + 112))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (state.tasks.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.CenterHorizontally),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(bottom = 30.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.empty_tasks_state),
                                style = MaterialTheme.typography.body1,
                                color = MaterialTheme.colors.onBackground
                            )
                        }
                        Row(modifier = Modifier.wrapContentSize()) {
                            Icon(
                                painter = painterResource(id = R.drawable.dr_sleeping_sloth),
                                tint = MaterialTheme.colors.primary,
                                contentDescription = stringResource(R.string.sleeping_sloth_drawing)
                            )
                        }
                    }
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(state.tasks) { task ->
                        if (showCompletedTasks.value || !showCompletedTasks.value && task.state == "todo") {
                            TaskItem(
                                task = task,
                                onClick = {
                                    expandedTaskMenu.value = true
                                    pickedTaskId.value = task
                                }
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }
    }
}
/*
* onDeleteClick = {
*   viewModel.onEvent(TasksEvent.DeleteTask(task))
*   scope.launch {
*       val result = scaffoldState.snackBarHostState.showSnackBar(
*           message = "Task Deleted",
*           actionLabel = "Undo"
*       )
*       if(result == SnackBarResult.ActionPerformed) {
*           viewModel.onEvent(TasksEvent.RestoreTask)
*       }
*   }
* }
* */