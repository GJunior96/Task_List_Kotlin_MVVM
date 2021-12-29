package com.example.tasklist.feature_task.presentation.settings

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

const val SHARED_PREFS = "sharedPrefs"
const val SHOW_COMPLETED_TASKS = "showCompletedTasks"
const val ALLOW_TO_EDIT_TASK_DATE = "allowToEditTaskDate"
const val ALLOW_TO_EDIT_TASK_HOUR = "allowToEditTaskHour"
const val SELECTED_THEME = "selected theme"

@HiltViewModel
class SettingsViewModel @Inject constructor(
    @ApplicationContext context: Context
): ViewModel() {
    private val sharedPref = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
    private val editor = sharedPref.edit()

    private val _showCompletedTasks =
        mutableStateOf(SwitchState(sharedPref.getBoolean(SHOW_COMPLETED_TASKS, true)))
    private val _allowToEditTaskDate =
        mutableStateOf(SwitchState(sharedPref.getBoolean(ALLOW_TO_EDIT_TASK_DATE, false)))
    private val _allowToEditTaskHour =
        mutableStateOf(SwitchState(sharedPref.getBoolean(ALLOW_TO_EDIT_TASK_HOUR, true)))
    private val _selectedTheme =
        //mutableStateOf(ThemeState(sharedPref.getInt(SELECTED_THEME, 0)))
        mutableStateOf(ThemeState(sharedPref.getString(SELECTED_THEME, "purple")!!))

    val showCompletedTasks: State<SwitchState> = _showCompletedTasks
    val allowToEditTaskDate: State<SwitchState> = _allowToEditTaskDate
    val allowToEditTaskHour: State<SwitchState> = _allowToEditTaskHour
    val selectedTheme: State<ThemeState?> = _selectedTheme

    fun onEvent(event: SettingsEvent) {
        when(event) {
            is SettingsEvent.ChangeSwitchState -> {
                when(event.switch) {
                    "showCompletedTasks" -> {
                        _showCompletedTasks.value = showCompletedTasks.value.copy(state = event.state)
                        editor.putBoolean(SHOW_COMPLETED_TASKS, event.state)
                        editor.apply()
                    }
                    "allowToEditTaskDate" -> {
                        _allowToEditTaskDate.value = allowToEditTaskDate.value.copy(state = event.state)
                        editor.putBoolean(ALLOW_TO_EDIT_TASK_DATE, event.state)
                        editor.apply()
                    }
                    "allowToEditTaskHour" -> {
                        _allowToEditTaskHour.value = allowToEditTaskHour.value.copy(state = event.state)
                        editor.putBoolean(ALLOW_TO_EDIT_TASK_HOUR, event.state)
                        editor.apply()
                    }
                }
            }
            is SettingsEvent.ChangeTheme -> {
                _selectedTheme.value = selectedTheme.value!!.copy(theme = event.theme)
                editor.putString(SELECTED_THEME, event.theme)
                editor.apply()
            }
        }
    }
}