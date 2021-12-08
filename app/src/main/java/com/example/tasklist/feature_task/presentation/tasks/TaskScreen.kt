package com.example.tasklist.feature_task.presentation.tasks

import android.app.Dialog
import android.util.DisplayMetrics
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavArgument
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navArgument
import androidx.navigation.navOptions
import com.example.tasklist.R
import com.example.tasklist.feature_task.domain.model.Task
import com.example.tasklist.feature_task.domain.util.TasksEvent
import com.example.tasklist.feature_task.presentation.add_edit_task.AddEditTaskEvent
import com.example.tasklist.feature_task.presentation.tasks.components.*
import com.example.tasklist.feature_task.presentation.ui.theme.TaskListAppTheme
import com.example.tasklist.feature_task.presentation.util.Screen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.absoluteValue

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun TaskScreen(
    navController: NavController,
    viewModel: TasksViewModel = hiltViewModel(),
    screenWidthDp: Float,
    screenWidthPx: Int
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val patternFormatDate = SimpleDateFormat("dd/MM")
    val patternFormatMonth = SimpleDateFormat("MMMM")
    var calendar = Calendar.getInstance()
    var expandedCalendarDialog = remember { mutableStateOf(false) }
    var month = remember { mutableStateOf(calendar) }
    var listDays =
        remember { mutableStateOf(viewModel.fillDaysList(month.value.get(Calendar.MONTH))) }

    val listState = rememberLazyListState()

    val pointer = remember { mutableStateOf(month.value.get(Calendar.DAY_OF_MONTH)) }

    val expandedTaskMenu = remember { mutableStateOf(false) }
    val expandedEditTask = remember { mutableStateOf(false) }
    val pickedTaskId = remember { mutableStateOf<Task?>(null) }


    screenWidthDp.dp

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
            onDismissRequest = { expandedEditTask.value = false },
            onClick = { }
        )
    }

    if (expandedCalendarDialog.value) {
        DatePicker(
            onDateSelected = {
                month.value = it
                listDays.value.clear()
                listDays.value = viewModel.fillDaysList(it.get(Calendar.MONTH))
                viewModel.date = patternFormatDate.format(it.time)
                viewModel.onEvent(TasksEvent.ChangeDay)
            },
            onDismissRequest = { expandedCalendarDialog.value = false }
        )
    }

    Scaffold(
        topBar = { CustomTopBarView(navController = navController, "Daily Tasks") },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddTaskScreen.route)
                },
                backgroundColor = MaterialTheme.colors.primary,
            ) {
                Icon(imageVector = Icons.Default.Add, "Add Task")
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
                        .background(Color.Green)
                        .padding(start = 46.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextButton(
                        modifier = Modifier.background(Color.Cyan),
                        onClick = { expandedCalendarDialog.value = true }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            modifier = Modifier
                                .size(20.dp)
                                .align(Alignment.CenterVertically)
                                .background(Color.Blue),
                            contentDescription = "Drop Down"
                        )
                        Text(
                            modifier = Modifier.background(Color.Red),
                            text = patternFormatMonth.format(month.value.time),
                            style = MaterialTheme.typography.h4,
                            color = MaterialTheme.colors.onBackground,
                            textAlign = Center
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .wrapContentSize()
                        .background(Color.Yellow),
                ) {
                    IconButton(
                        modifier = Modifier.background(Color.Yellow),
                        onClick = {
                            viewModel.onEvent(TasksEvent.ToggleOrderSection)
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Sort,
                            modifier = Modifier.size(30.dp),
                            tint = MaterialTheme.colors.onBackground,
                            contentDescription = "Sort"
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
                    onOrderChange = {
                        //viewModel.onEvent(TasksEvent.Order(task = , taskOrder = state.taskOrder))
                        viewModel.onEvent(TasksEvent.Order(it))
                    }
                )
            }

            //Spacer(modifier = Modifier.height(TaskListAppTheme.paddings.tinyPadding))

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Yellow),
                state = listState,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(listDays.value, key = { it.day }) { item ->
                    /*if (item.day == 1) {
                        Box(modifier = Modifier.size(width = ((screenWidthDp/2) - 42.5F).dp, height = 0.dp)) {  }
                    }*/
                    CustomWeekDayView(
                        item,
                        modifier = Modifier
                            /*.graphicsLayer {
                                val value =
                                    1 - (listState.layoutInfo.normalizedItemPosition(item.day).absoluteValue) * 0.15F
                                alpha = value
                                scaleX = value
                                scaleY = value
                            }*/
                            .clickable {
                                //item.clicked.value = listState.layoutInfo.setItemClicked(item)
                                month.value.set(Calendar.DAY_OF_MONTH, item.day)
                                viewModel.date = patternFormatDate.format(month.value.time)
                                viewModel.onEvent(TasksEvent.ChangeDay)
                                pointer.value = item.day

                               /*scope.launch {
                                    when(pointer.value) {
                                        (listDays.value.size - 5) -> {
                                            listState.scrollToItem(pointer.value)
                                            listState.animateScrollBy(((-screenWidthPx/2) + 376).toFloat())
                                        }
                                        (listDays.value.size - 4) -> {
                                            listState.scrollToItem(pointer.value - 1)
                                            listState.animateScrollBy(((-screenWidthPx/2) + 640).toFloat())
                                        }
                                        else -> {
                                            listState.scrollToItem(pointer.value + 1)
                                            listState.scrollBy(((-screenWidthPx/2) + 112).toFloat())
                                        }
                                    }
                                }*/
                            },
                        boxModifier = Modifier
                        /*.graphicsLayer {
                                val value =
                                    1 - (listState.layoutInfo.normalizedItemPosition(item.day).absoluteValue) * 0.15F
                                scaleX = value
                                scaleY = value
                            }*/,
                        emptyBoxModifier = Modifier
                            .size(width = ((screenWidthDp / 4) - 29.25).dp, height = 2.dp)
                            .background(Color.Red),
                        pointer = pointer
                    )
                    /*if (item.day == listDays.value.lastIndex + 1) {
                        Box(modifier = Modifier.size(width = (85).dp, height = 0.dp)) {  }
                    }*/
                }

                scope.launch {
                    pointer.value = month.value.get(Calendar.DAY_OF_MONTH)
                    when (month.value.get(Calendar.DAY_OF_MONTH)) {
                        (listDays.value.size - 5) -> {
                            listState.scrollToItem(month.value.get(Calendar.DAY_OF_MONTH))
                            listState.scrollBy(((-screenWidthPx / 2) + 376).toFloat())
                        }
                        (listDays.value.size - 4) -> {
                            listState.scrollToItem(month.value.get(Calendar.DAY_OF_MONTH) - 1)
                            listState.scrollBy(((-screenWidthPx / 2) + 640).toFloat())
                        }
                        else -> {
                            listState.scrollToItem(month.value.get(Calendar.DAY_OF_MONTH) + 1)
                            listState.scrollBy(((-screenWidthPx / 2) + 112).toFloat())
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.tasks) { task ->
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

            /* if (state.tasks.isEmpty()) {
                /*Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.CenterHorizontally)
                        .background(Color.Gray),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center

                    ) {
                        Row(
                            modifier = Modifier
                                .wrapContentSize()
                                .background(Color.Red)
                                .padding(bottom = 30.dp)
                        ) {
                            Text(
                                text = "Ops! You don't have\nany tasks for today.",
                                style = MaterialTheme.typography.body1,
                                color = MaterialTheme.colors.onBackground
                            )
                        }
                        Row(
                            modifier = Modifier
                                .wrapContentSize()
                                .background(Color.Green)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.dr_sleeping_sloth),
                                contentDescription = "Sleeping Sloth"
                            )
                        }
                    }
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(state.tasks) { task ->
                        TaskItem(
                            task = task,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    expandedTaskMenu.value = true
                                }
                        )
                        Log.i("teste", "aqui chegou")
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }*/
            }*/
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