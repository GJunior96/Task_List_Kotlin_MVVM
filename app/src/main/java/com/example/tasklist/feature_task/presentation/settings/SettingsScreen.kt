package com.example.tasklist.feature_task.presentation.settings

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tasklist.R
import com.example.tasklist.feature_task.presentation.settings.components.ThemeDropDownMenu
import com.example.tasklist.feature_task.presentation.tasks.components.CustomTopBarView

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel(),
    themeType: MutableState<String?>,
    isChangingTheme: MutableState<Boolean>,
    navState: MutableState<Bundle?>
) {

    val showCompletedTasks = viewModel.showCompletedTasks
    val allowToEditHour = viewModel.allowToEditTaskHour
    val allowToEditDate = viewModel.allowToEditTaskDate
    val selectedTheme = viewModel.selectedTheme
    val switchColors = SwitchDefaults.colors(
        checkedThumbColor = MaterialTheme.colors.secondary,
        checkedTrackColor = MaterialTheme.colors.primary,
        checkedTrackAlpha = 1f,
        uncheckedThumbColor = MaterialTheme.colors.secondary,
        uncheckedTrackColor = MaterialTheme.colors.secondary,
        uncheckedTrackAlpha = 1f
    )
    val themeList = listOf("black", "blue", "purple", "white")
    val expandedThemeDropdownMenu = remember { mutableStateOf(false) }

    Scaffold(
        topBar = { CustomTopBarView(
            navController = navController,
            title = stringResource(R.string.settings_screen_toolbar_title)
        ) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = stringResource(R.string.show_completed_tasks))
                Switch(
                    checked = showCompletedTasks.value.state,
                    onCheckedChange = {
                        viewModel.onEvent(
                            SettingsEvent.ChangeSwitchState("showCompletedTasks", it)
                        )
                    },
                    colors = switchColors
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = stringResource(R.string.allow_to_edit_task_hour))
                Switch(
                    checked = allowToEditHour.value.state,
                    onCheckedChange = {
                        viewModel.onEvent(
                            SettingsEvent.ChangeSwitchState("allowToEditTaskHour", it)
                        )
                    },
                    colors = switchColors
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = stringResource(R.string.allow_to_edit_task_date))
                Switch(
                    checked = allowToEditDate.value.state,
                    onCheckedChange = {
                        viewModel.onEvent(
                            SettingsEvent.ChangeSwitchState("allowToEditTaskDate", it)
                        )
                    },
                    colors = switchColors
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = stringResource(R.string.choose_the_theme))
                Box(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.large)
                        .background(MaterialTheme.colors.primary)
                        .wrapContentSize(Alignment.CenterEnd)
                        .clickable {
                            expandedThemeDropdownMenu.value = true
                        }
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = selectedTheme.value?.theme ?: "",
                            color = MaterialTheme.colors.onPrimary
                        )
                        Icon(
                            imageVector = Icons.Filled.ArrowDropDown,
                            tint = MaterialTheme.colors.onPrimary,
                            contentDescription = stringResource(R.string.dropdown_arrow_description)
                        )
                    }

                    if (expandedThemeDropdownMenu.value) {
                        ThemeDropDownMenu(
                            list = themeList,
                            onDismissRequest = { expandedThemeDropdownMenu.value = false },
                            selectedItem = {
                                viewModel.onEvent(SettingsEvent.ChangeTheme(it))
                                isChangingTheme.value = true
                                themeType.value  = it
                                navState.value = navController.saveState()
                            }
                        )
                    }
                }
            }
        }
    }
}