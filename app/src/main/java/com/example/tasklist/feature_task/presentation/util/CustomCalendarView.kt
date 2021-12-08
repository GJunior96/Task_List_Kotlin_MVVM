package com.example.tasklist.feature_task.presentation.tasks.components

import android.view.ContextThemeWrapper
import android.widget.CalendarView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.viewinterop.AndroidView
import com.example.tasklist.R
import java.time.Year
import java.util.*

@Composable
fun CustomCalendarView(onDateSelected: (Calendar) -> Unit) {
    val calendar = Calendar.getInstance()
    val maxDate = calendar.getActualMaximum(Calendar.DAY_OF_YEAR)

    AndroidView(
        modifier = Modifier
            .wrapContentSize(),
        factory = { context ->
            CalendarView(ContextThemeWrapper(context, R.style.CalendarViewCustom))
        },
        update = { view ->
            view.minDate = calendar.apply { set(calendar.get(Calendar.YEAR), 0, 1) }.timeInMillis
            view.maxDate = calendar.apply { set(calendar.get(Calendar.YEAR), 11, 31) }.timeInMillis

            view.setOnDateChangeListener { _, year, month, dayOfMonth ->
                onDateSelected(
                    Calendar
                        .getInstance()
                        .apply { set(year, month, dayOfMonth) }
                )
            }
        }
    )
}