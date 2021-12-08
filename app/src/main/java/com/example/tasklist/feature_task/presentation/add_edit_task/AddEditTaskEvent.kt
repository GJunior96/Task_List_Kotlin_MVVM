package com.example.tasklist.feature_task.presentation.add_edit_task

import androidx.compose.ui.focus.FocusState

sealed class AddEditTaskEvent {
    data class SelectedTask(val value: Int?): AddEditTaskEvent()
    data class EnteredDescription(val value: String): AddEditTaskEvent()
    data class ChangeDescriptionFocus(val focusState: FocusState): AddEditTaskEvent()
    data class EnteredDate(val value: String): AddEditTaskEvent()
    data class ChangeDateFocus(val focusState: FocusState): AddEditTaskEvent()
    data class EnteredHour(val value: String): AddEditTaskEvent()
    data class ChangeHourFocus(val focusState: FocusState): AddEditTaskEvent()
    object SaveTask: AddEditTaskEvent()
}
