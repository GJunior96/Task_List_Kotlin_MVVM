package com.example.tasklist.feature_task.presentation.util

import android.content.Context
import android.view.ContextThemeWrapper
import android.widget.CalendarView
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
import java.util.*

@Composable
fun CustomCalendarView(onDateSelected: (Calendar) -> Unit, isNewTask: Boolean) {
    val currentContext = LocalContext.current
    val sharedPref = currentContext.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
    val selectedTheme = remember { mutableStateOf(sharedPref.getString(SELECTED_THEME, "purple")) }
    val calendar = Calendar.getInstance()

    AndroidView(
        modifier = Modifier.wrapContentSize(),
        factory = { context ->
            CalendarView(ContextThemeWrapper(context, currentTheme(selectedTheme.value)))
        },
        update = { view ->
            view.minDate =
                if (!isNewTask) {
                    calendar.apply {
                        set(calendar.get(Calendar.YEAR), 0, 1)
                    }.timeInMillis
                }
                else {
                    calendar.apply {
                        set(calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH))
                    }.timeInMillis
                }
            view.maxDate = calendar.apply { set(calendar.get(Calendar.YEAR), 11, 31) }.timeInMillis

            view.setOnDateChangeListener { _, year, month, dayOfMonth ->
                view.date = Calendar.getInstance().apply { set(year,month,dayOfMonth) }.timeInMillis
                onDateSelected(
                    Calendar
                        .getInstance()
                        .apply { set(year, month, dayOfMonth) }
                )
            }
        }
    )
}