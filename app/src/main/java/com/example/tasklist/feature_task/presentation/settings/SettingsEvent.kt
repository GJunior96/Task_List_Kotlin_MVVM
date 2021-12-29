package com.example.tasklist.feature_task.presentation.settings

sealed class SettingsEvent {
    data class ChangeSwitchState(val switch: String, val state: Boolean): SettingsEvent()
    data class ChangeTheme(val theme: String): SettingsEvent()
}
