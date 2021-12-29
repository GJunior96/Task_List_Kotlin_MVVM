package com.example.tasklist.feature_task.presentation.util

import android.content.Context
import android.view.ContextThemeWrapper
import android.widget.TimePicker
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.tasklist.R
import com.example.tasklist.feature_task.presentation.settings.SELECTED_THEME
import com.example.tasklist.feature_task.presentation.settings.SHARED_PREFS

@Composable
fun CustomTimeView(onTimeSelected: (String) -> Unit) {
    val currentContext = LocalContext.current
    val sharedPref = currentContext.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
    val selectedTheme = remember { mutableStateOf(sharedPref.getString(SELECTED_THEME, "purple")) }
    AndroidView(
        modifier = Modifier.wrapContentSize(),
        factory = { context ->
            TimePicker(ContextThemeWrapper(context, currentTheme(selectedTheme.value)))
        },
        update = { view ->
            view.setIs24HourView(true)
            view.setOnTimeChangedListener { _, hour, minute ->
                onTimeSelected(
                    "$hour:$minute"
                )
            }
        }
    )
}